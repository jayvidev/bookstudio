package com.bookstudio.circulation.loan;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.bookstudio.IntegrationTest;
import com.bookstudio.circulation.LoanItemReturned;

@IntegrationTest
@RecordApplicationEvents
class LoanEventsIntegrationTest {

    @Autowired
    MockMvcTester mvc;

    @Autowired
    JdbcClient jdbc;

    @Autowired
    ApplicationEvents events;

    @Test
    void returningAnItemPublishesLoanItemReturned() {
        Map<String, Object> item = jdbc.sql("""
                SELECT li.loan_id, li.copy_id, li.due_date, l.reader_id, l.code FROM loan_items li
                JOIN loans l ON l.id = li.loan_id
                WHERE li.status = 'PRESTADO' AND li.due_date < CURRENT_DATE ORDER BY li.loan_id LIMIT 1""")
                .query().singleRow();
        LocalDate dueDate = ((java.sql.Date) item.get("due_date")).toLocalDate();

        assertThat(mvc.post().uri("/loans/%s/items/%s/return".formatted(item.get("loan_id"), item.get("copy_id"))))
                .hasStatusOk();

        assertThat(events.stream(LoanItemReturned.class)).singleElement().satisfies(event -> {
            assertThat(event.loanId()).isEqualTo(item.get("loan_id"));
            assertThat(event.loanCode()).isEqualTo(item.get("code"));
            assertThat(event.copyId()).isEqualTo(item.get("copy_id"));
            assertThat(event.readerId()).isEqualTo(item.get("reader_id"));
            assertThat(event.returnDate()).isEqualTo(LocalDate.now());
            assertThat(event.daysLate()).isEqualTo(ChronoUnit.DAYS.between(dueDate, LocalDate.now()));
        });
    }

    @Test
    void otherStatusChangesPublishNothing() throws Exception {
        long copyId = jdbc.sql("SELECT id FROM copies WHERE status = 'DISPONIBLE' ORDER BY id LIMIT 1").query(Long.class).single();
        String due = LocalDate.now().plusDays(7).toString();
        var created = mvc.post().uri("/loans").contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("""
                        {"readerId": 1, "items": [{"copyId": %d, "dueDate": "%s"}]}""".formatted(copyId, due)).exchange();
        Number loanId = com.jayway.jsonpath.JsonPath.read(created.getResponse().getContentAsString(), "$.data.id");

        assertThat(mvc.put().uri("/loans/" + loanId).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("""
                        {"readerId": 1, "items": [{"copyId": %d, "dueDate": "%s", "status": "RETRASADO"}]}"""
                        .formatted(copyId, due))).hasStatusOk();

        assertThat(events.stream(LoanItemReturned.class)).isEmpty();
    }
}
