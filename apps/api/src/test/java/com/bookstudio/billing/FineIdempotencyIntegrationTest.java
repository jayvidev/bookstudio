package com.bookstudio.billing;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.bookstudio.IntegrationTest;
import com.bookstudio.billing.fine.application.FineService;

@IntegrationTest
class FineIdempotencyIntegrationTest {

    @Autowired
    FineService fineService;

    @Autowired
    MockMvcTester mvc;

    @Autowired
    JdbcClient jdbc;

    @Test
    void aRedeliveredEventDoesNotFineTwice() {
        Map<String, Object> item = itemWithoutFine();
        long loanId = ((Number) item.get("loan_id")).longValue();
        long copyId = ((Number) item.get("copy_id")).longValue();

        assertThat(fineService.issueOverdueFine(loanId, copyId, 3, LocalDate.now())).isPresent();
        assertThat(fineService.issueOverdueFine(loanId, copyId, 3, LocalDate.now())).isEmpty();
        assertThat(jdbc.sql("SELECT COUNT(*) FROM fines WHERE loan_id = ? AND copy_id = ?")
                .params(loanId, copyId).query(Long.class).single()).isEqualTo(1);
    }

    @Test
    void aSecondManualFineForTheSameItemIsRejected() {
        Map<String, Object> fined = jdbc.sql("SELECT loan_id, copy_id FROM fines ORDER BY id LIMIT 1").query().singleRow();

        assertThat(mvc.post().uri("/fines").contentType(MediaType.APPLICATION_JSON).content("""
                {"loanItemId": {"loanId": %s, "copyId": %s}, "amount": 5.00, "daysLate": 2,
                 "status": "PENDIENTE", "issuedAt": "%s"}""".formatted(fined.get("loan_id"), fined.get("copy_id"), LocalDate.now())))
                .hasStatus(HttpStatus.CONFLICT)
                .bodyJson()
                .extractingPath("$.message").isEqualTo("This loan item already has a fine");
    }

    private Map<String, Object> itemWithoutFine() {
        return jdbc.sql("""
                SELECT li.loan_id, li.copy_id FROM loan_items li
                WHERE NOT EXISTS (SELECT 1 FROM fines f WHERE f.loan_id = li.loan_id AND f.copy_id = li.copy_id)
                ORDER BY li.loan_id LIMIT 1""").query().singleRow();
    }
}
