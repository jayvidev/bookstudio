package com.bookstudio.loan;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import com.bookstudio.IntegrationTest;
import com.jayway.jsonpath.JsonPath;

@IntegrationTest
class LoanRulesIntegrationTest {

    private static final String DUE = LocalDate.now().plusDays(14).toString();

    @Autowired
    MockMvcTester mvc;

    @Autowired
    JdbcClient jdbc;

    @Test
    void lendingACopyMarksItOnLoan() throws Exception {
        long copyId = availableCopy(0);

        createLoan(copyId);

        assertThat(copyStatus(copyId)).isEqualTo("PRESTADO");
    }

    @Test
    void cannotLendACopyThatIsAlreadyOnLoan() throws Exception {
        long copyId = availableCopy(0);
        createLoan(copyId);

        assertThat(post("/loans", createJson(copyId)))
                .hasStatus(HttpStatus.CONFLICT)
                .bodyJson()
                .extractingPath("$.message").asString()
                .matches("Copy EJE-\\d{4}-\\d{5} is not available \\(status: PRESTADO\\)");
    }

    @Test
    void cannotLendACopyInMaintenance() {
        long copyId = jdbc.sql("SELECT id FROM copies WHERE status = 'MANTENIMIENTO' LIMIT 1").query(Long.class).single();

        assertThat(post("/loans", createJson(copyId))).hasStatus(HttpStatus.CONFLICT);
    }

    @Test
    void rejectsUnknownCopy() {
        assertThat(post("/loans", createJson(999_999))).hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void rejectsTheSameCopyTwiceInOneRequest() {
        long copyId = availableCopy(0);

        assertThat(post("/loans", createJson(copyId, copyId)))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.message").isEqualTo("Copy %d appears more than once in the loan".formatted(copyId));
    }

    @Test
    void returningAnItemRecordsTheDateAndFreesTheCopy() throws Exception {
        long copyId = availableCopy(0);
        long loanId = createLoan(copyId);

        assertThat(put("/loans/" + loanId, updateJson(item(copyId, "DEVUELTO")))).hasStatusOk();

        assertThat(copyStatus(copyId)).isEqualTo("DISPONIBLE");
        assertThat(jdbc.sql("SELECT return_date FROM loan_items WHERE loan_id = ? AND copy_id = ?")
                .params(loanId, copyId).query(LocalDate.class).single()).isEqualTo(LocalDate.now());
    }

    @Test
    void keepingTheSameCopyOnUpdateSucceeds() throws Exception {
        long copyId = availableCopy(0);
        long loanId = createLoan(copyId);

        assertThat(put("/loans/" + loanId, updateJson(item(copyId, "PRESTADO"))))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.data.itemCount").isEqualTo(1);
        assertThat(copyStatus(copyId)).isEqualTo("PRESTADO");
    }

    @Test
    void removingAnItemFreesItsCopyAndAddingOneLendsIt() throws Exception {
        long removed = availableCopy(0);
        long added = availableCopy(1);
        long loanId = createLoan(removed);

        assertThat(put("/loans/" + loanId, updateJson(item(added, "PRESTADO")))).hasStatusOk();

        assertThat(copyStatus(removed)).isEqualTo("DISPONIBLE");
        assertThat(copyStatus(added)).isEqualTo("PRESTADO");
        assertThat(count("SELECT COUNT(*) FROM loan_items WHERE loan_id = " + loanId)).isEqualTo(1);
    }

    @Test
    void losingAnItemMarksTheCopyLost() throws Exception {
        long copyId = availableCopy(0);
        long loanId = createLoan(copyId);

        assertThat(put("/loans/" + loanId, updateJson(item(copyId, "EXTRAVIADO")))).hasStatusOk();

        assertThat(copyStatus(copyId)).isEqualTo("EXTRAVIADO");
    }

    @Test
    void newItemsOnUpdateMustStartOnLoan() throws Exception {
        long loanId = createLoan(availableCopy(0));
        long other = availableCopy(1);

        assertThat(put("/loans/" + loanId, updateJson(item(other, "DEVUELTO"))))
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    private long createLoan(long... copyIds) throws Exception {
        MvcTestResult result = post("/loans", createJson(copyIds));
        assertThat(result).hasStatus(HttpStatus.CREATED);
        return JsonPath.<Number>read(result.getResponse().getContentAsString(), "$.data.id").longValue();
    }

    private MvcTestResult post(String uri, String json) {
        return mvc.post().uri(uri).contentType(MediaType.APPLICATION_JSON).content(json).exchange();
    }

    private MvcTestResult put(String uri, String json) {
        return mvc.put().uri(uri).contentType(MediaType.APPLICATION_JSON).content(json).exchange();
    }

    private static String createJson(long... copyIds) {
        StringBuilder items = new StringBuilder();
        for (long copyId : copyIds) {
            items.append(items.isEmpty() ? "" : ",").append("""
                    {"copyId": %d, "dueDate": "%s"}""".formatted(copyId, DUE));
        }
        return """
                {"readerId": 1, "observation": null, "items": [%s]}""".formatted(items);
    }

    private static String item(long copyId, String status) {
        return """
                {"copyId": %d, "dueDate": "%s", "status": "%s"}""".formatted(copyId, DUE, status);
    }

    private static String updateJson(String... items) {
        return """
                {"readerId": 1, "observation": "updated", "items": [%s]}""".formatted(String.join(",", items));
    }

    private long availableCopy(int offset) {
        return jdbc.sql("SELECT id FROM copies WHERE status = 'DISPONIBLE' ORDER BY id OFFSET ? LIMIT 1")
                .param(offset).query(Long.class).single();
    }

    private String copyStatus(long copyId) {
        return jdbc.sql("SELECT status FROM copies WHERE id = ?").param(copyId).query(String.class).single();
    }

    private long count(String sql) {
        return jdbc.sql(sql).query(Long.class).single();
    }
}
