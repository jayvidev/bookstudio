package com.bookstudio.loan;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import com.bookstudio.IntegrationTest;
import com.jayway.jsonpath.JsonPath;

@IntegrationTest
class LoanPaginationIntegrationTest {

    @Autowired
    MockMvcTester mvc;

    @Autowired
    JdbcClient jdbc;

    @Test
    void returnsFirstPageNewestFirstByDefault() throws Exception {
        long total = count("SELECT COUNT(*) FROM loans");
        List<Long> newest = ids("SELECT id FROM loans ORDER BY id DESC LIMIT 20");

        MvcTestResult result = get("/loans");

        assertThat(result).hasStatusOk().bodyJson().satisfies(json -> {
            json.assertThat().extractingPath("$.data.page").isEqualTo(0);
            json.assertThat().extractingPath("$.data.size").isEqualTo(20);
            json.assertThat().extractingPath("$.data.totalElements").isEqualTo((int) total);
            json.assertThat().extractingPath("$.data.totalPages").isEqualTo((int) Math.ceil(total / 20.0));
        });
        assertThat(contentIds(result)).isEqualTo(newest);
    }

    @Test
    void returnsRequestedPage() throws Exception {
        List<Long> secondPage = ids("SELECT id FROM loans ORDER BY id DESC OFFSET 10 LIMIT 10");

        assertThat(contentIds(get("/loans?page=1&size=10"))).isEqualTo(secondPage);
    }

    @Test
    void sortsByAllowedProperty() throws Exception {
        List<String> dates = JsonPath.read(body(get("/loans?size=100&sort=loanDate,asc")), "$.data.content[*].loanDate");

        assertThat(dates).isNotEmpty().isSortedAccordingTo(Comparator.naturalOrder());
    }

    @Test
    void rejectsUnknownSortProperty() {
        assertThat(mvc.get().uri("/loans?sort=observation"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.message").isEqualTo("Cannot sort by 'observation'. Allowed: [code, id, loanDate]");
    }

    @Test
    void capsPageSizeAtOneHundred() {
        assertThat(mvc.get().uri("/loans?size=500"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.data.size").isEqualTo(100);
    }

    @Test
    void filtersByItemStatus() throws Exception {
        List<Long> expected = ids("""
                SELECT DISTINCT loan_id FROM loan_items WHERE status = 'DEVUELTO' ORDER BY loan_id DESC""");

        assertThat(contentIds(get("/loans?size=100&status=DEVUELTO"))).isNotEmpty().isEqualTo(expected);
    }

    @Test
    void filtersByReader() throws Exception {
        long readerId = count("SELECT reader_id FROM loans GROUP BY reader_id ORDER BY COUNT(*) DESC, reader_id LIMIT 1");
        List<Long> expected = ids("SELECT id FROM loans WHERE reader_id = " + readerId + " ORDER BY id DESC");

        assertThat(contentIds(get("/loans?size=100&readerId=" + readerId))).isEqualTo(expected);
    }

    @Test
    void searchesByCodeFragment() throws Exception {
        String code = jdbc.sql("SELECT code FROM loans ORDER BY id LIMIT 1").query(String.class).single();
        List<Long> expected = ids("SELECT id FROM loans WHERE code ILIKE '%" + code.substring(4) + "%' ORDER BY id DESC");

        assertThat(contentIds(get("/loans?size=100&search=" + code.substring(4).toLowerCase())))
                .isNotEmpty().isEqualTo(expected);
    }

    @Test
    void searchesByReaderNameIgnoringCase() throws Exception {
        var reader = jdbc.sql("SELECT r.id, r.first_name FROM readers r JOIN loans l ON l.reader_id = r.id ORDER BY r.id LIMIT 1")
                .query().singleRow();
        String name = ((String) reader.get("first_name")).toUpperCase();
        List<Long> expected = ids("""
                SELECT l.id FROM loans l JOIN readers r ON r.id = l.reader_id
                WHERE (r.first_name || ' ' || r.last_name) ILIKE '%%%s%%' OR l.code ILIKE '%%%s%%'
                ORDER BY l.id DESC""".formatted(name, name));

        assertThat(contentIds(get("/loans?size=100&search=" + name))).isNotEmpty().isEqualTo(expected);
    }

    @Test
    void filtersByDateRange() throws Exception {
        LocalDate from = jdbc.sql("SELECT MIN(loan_date) + 30 FROM loans").query(LocalDate.class).single();
        LocalDate to = from.plusDays(60);
        List<Long> expected = ids("SELECT id FROM loans WHERE loan_date BETWEEN '%s' AND '%s' ORDER BY id DESC".formatted(from, to));

        assertThat(contentIds(get("/loans?size=100&from=" + from + "&to=" + to))).isEqualTo(expected);
    }

    @Test
    void combinesFilters() throws Exception {
        var loan = jdbc.sql("""
                SELECT l.reader_id, li.status FROM loans l JOIN loan_items li ON li.loan_id = l.id ORDER BY l.id LIMIT 1""")
                .query().singleRow();
        List<Long> expected = ids("""
                SELECT l.id FROM loans l
                WHERE l.reader_id = %s
                  AND EXISTS (SELECT 1 FROM loan_items li WHERE li.loan_id = l.id AND li.status = '%s')
                ORDER BY l.loan_date DESC, l.id DESC""".formatted(loan.get("reader_id"), loan.get("status")));

        assertThat(contentIds(get("/loans?size=100&sort=loanDate,desc&sort=id,desc&readerId=%s&status=%s"
                .formatted(loan.get("reader_id"), loan.get("status")))))
                .isNotEmpty().isEqualTo(expected);
    }

    @Test
    void returnsEmptyPageWhenNothingMatches() {
        assertThat(mvc.get().uri("/loans?search=zzzz-no-match"))
                .hasStatusOk()
                .bodyJson()
                .satisfies(json -> {
                    json.assertThat().extractingPath("$.message").isEqualTo("No loans found");
                    json.assertThat().extractingPath("$.data.totalElements").isEqualTo(0);
                    json.assertThat().extractingPath("$.data.content").asArray().isEmpty();
                });
    }

    @Test
    void rejectsInvalidStatusWith400() {
        assertThat(mvc.get().uri("/loans?status=PERDIDO")).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void rejectsInvalidDateWith400() {
        assertThat(mvc.get().uri("/loans?from=2026-13-40")).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void rejectsReversedDateRange() {
        assertThat(mvc.get().uri("/loans?from=2026-02-01&to=2026-01-01"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.errors[0].message").isEqualTo("'from' must not be after 'to'");
    }

    private MvcTestResult get(String uri) {
        return mvc.get().uri(uri).exchange();
    }

    private static String body(MvcTestResult result) throws Exception {
        return result.getResponse().getContentAsString();
    }

    private static List<Long> contentIds(MvcTestResult result) throws Exception {
        assertThat(result).hasStatusOk();
        List<Number> ids = JsonPath.read(body(result), "$.data.content[*].id");
        return ids.stream().map(Number::longValue).toList();
    }

    private long count(String sql) {
        return jdbc.sql(sql).query(Long.class).single();
    }

    private List<Long> ids(String sql) {
        return jdbc.sql(sql).query(Long.class).list();
    }
}
