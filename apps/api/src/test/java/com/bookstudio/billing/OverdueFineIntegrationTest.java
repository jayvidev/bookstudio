package com.bookstudio.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookstudio.IntegrationTest;

/**
 * Circulation -> billing through an event, as in production: the return commits,
 * then the listener runs asynchronously in its own transaction.
 */
@IntegrationTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class OverdueFineIntegrationTest {

    @Autowired
    MockMvcTester mvc;

    @Autowired
    JdbcClient jdbc;

    private Map<String, Object> item;

    @BeforeEach
    void pickAnOverdueItemWithoutFine() {
        item = jdbc.sql("""
                SELECT li.loan_id, li.copy_id, li.due_date, li.status FROM loan_items li
                WHERE li.status IN ('PRESTADO', 'RETRASADO') AND li.due_date < CURRENT_DATE
                  AND NOT EXISTS (SELECT 1 FROM fines f WHERE f.loan_id = li.loan_id AND f.copy_id = li.copy_id)
                ORDER BY li.loan_id LIMIT 1""").query().singleRow();
    }

    @AfterEach
    void restore() {
        jdbc.sql("DELETE FROM fines WHERE loan_id = ? AND copy_id = ?")
                .params(item.get("loan_id"), item.get("copy_id")).update();
        jdbc.sql("UPDATE loan_items SET status = ?, return_date = NULL WHERE loan_id = ? AND copy_id = ?")
                .params(item.get("status"), item.get("loan_id"), item.get("copy_id")).update();
        jdbc.sql("UPDATE copies SET status = 'PRESTADO' WHERE id = ?").param(item.get("copy_id")).update();
    }

    @Test
    void returningLateIssuesAnOverdueFine() {
        long daysLate = ChronoUnit.DAYS.between(((java.sql.Date) item.get("due_date")).toLocalDate(), LocalDate.now());

        assertThat(mvc.post().uri("/loans/%s/items/%s/return".formatted(item.get("loan_id"), item.get("copy_id"))))
                .hasStatusOk();

        await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
            Map<String, Object> fine = jdbc.sql("""
                    SELECT days_late, amount, status, issued_at, code FROM fines WHERE loan_id = ? AND copy_id = ?""")
                    .params(item.get("loan_id"), item.get("copy_id")).query().singleRow();

            assertThat(((Number) fine.get("days_late")).longValue()).isEqualTo(daysLate);
            assertThat((BigDecimal) fine.get("amount")).isEqualByComparingTo(BigDecimal.valueOf(daysLate));
            assertThat(fine.get("status")).isEqualTo("PENDIENTE");
            assertThat(((java.sql.Date) fine.get("issued_at")).toLocalDate()).isEqualTo(LocalDate.now());
            assertThat((String) fine.get("code")).matches("MUL-\\d{4}-\\d{5}");
        });

        await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> assertThat(
                jdbc.sql("SELECT COUNT(*) FROM event_publication WHERE completion_date IS NULL")
                        .query(Long.class).single()).isZero());
    }
}
