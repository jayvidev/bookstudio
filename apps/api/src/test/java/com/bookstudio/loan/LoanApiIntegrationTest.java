package com.bookstudio.loan;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.bookstudio.IntegrationTest;

@IntegrationTest
class LoanApiIntegrationTest {

    @Autowired
    MockMvcTester mvc;

    @Autowired
    JdbcClient jdbc;

    @Test
    void listsSeededLoans() {
        long seeded = jdbc.sql("SELECT COUNT(*) FROM loans").query(Long.class).single();

        assertThat(mvc.get().uri("/loans"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.data.length()").isEqualTo((int) seeded);
    }

    @Test
    void returnsLoanDetailWithItems() {
        long loanId = jdbc.sql("SELECT loan_id FROM loan_items LIMIT 1").query(Long.class).single();

        assertThat(mvc.get().uri("/loans/{id}", loanId))
                .hasStatusOk()
                .bodyJson()
                .satisfies(json -> {
                    json.assertThat().extractingPath("$.data.id").isEqualTo((int) loanId);
                    json.assertThat().extractingPath("$.data.reader.fullName").isNotNull();
                    json.assertThat().extractingPath("$.data.items[0].copy.code").isNotNull();
                });
    }

    @Test
    void returns404WhenLoanDoesNotExist() {
        assertThat(mvc.get().uri("/loans/{id}", 999_999))
                .hasStatus(HttpStatus.NOT_FOUND)
                .bodyJson()
                .extractingPath("$.message").isEqualTo("Loan not found with ID: 999999");
    }

    @Test
    void returns400WhenIdIsNotNumeric() {
        assertThat(mvc.get().uri("/loans/abc")).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void createsLoanWithGeneratedCode() {
        long readerId = anyReaderId();
        long copyId = anyAvailableCopyId(0);

        assertThat(mvc.post().uri("/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createLoanJson(readerId, copyId)))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .satisfies(json -> {
                    json.assertThat().extractingPath("$.data.code").asString()
                            .matches("PRE-\\d{8}-\\d{5}");
                    json.assertThat().extractingPath("$.data.reader.id").isEqualTo((int) readerId);
                });
    }

    @Test
    void createResponseCountsCreatedItems() {
        assertThat(mvc.post().uri("/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createLoanJson(anyReaderId(), anyAvailableCopyId(0))))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .satisfies(json -> {
                    json.assertThat().extractingPath("$.data.itemCount").isEqualTo(1);
                    json.assertThat().extractingPath("$.data.statusCounts.borrowed").isEqualTo(1);
                });
    }

    @Test
    void rejectsLoanWithoutItems() {
        assertThat(mvc.post().uri("/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"readerId": %d, "items": []}
                        """.formatted(anyReaderId())))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.message").isEqualTo("Validation failed");
    }

    @Test
    void returns404WhenReaderDoesNotExist() {
        assertThat(mvc.post().uri("/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createLoanJson(999_999, anyAvailableCopyId(0))))
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    private long anyReaderId() {
        return jdbc.sql("SELECT id FROM readers WHERE status = 'ACTIVO' ORDER BY id LIMIT 1")
                .query(Long.class).single();
    }

    private long anyAvailableCopyId(int offset) {
        return jdbc.sql("SELECT id FROM copies WHERE status = 'DISPONIBLE' ORDER BY id OFFSET :offset LIMIT 1")
                .param("offset", offset)
                .query(Long.class).single();
    }

    private static String createLoanJson(long readerId, long copyId) {
        return """
                {"readerId": %d, "observation": "test", "items": [{"copyId": %d, "dueDate": "%s"}]}
                """.formatted(readerId, copyId, LocalDate.now().plusDays(7));
    }
}
