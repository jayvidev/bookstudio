package com.bookstudio;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import com.jayway.jsonpath.JsonPath;

/**
 * Every paginated list: one filter per resource, checked against plain SQL.
 */
@IntegrationTest
class ListFiltersIntegrationTest {

    @Autowired
    MockMvcTester mvc;

    @Autowired
    JdbcClient jdbc;

    static Stream<Arguments> filters() {
        return Stream.of(
                Arguments.of("/authors?nationalityId=1&status=ACTIVO",
                        "SELECT id FROM authors WHERE nationality_id = 1 AND status = 'ACTIVO'"),
                Arguments.of("/authors?search=garc",
                        "SELECT id FROM authors WHERE name ILIKE '%garc%'"),
                Arguments.of("/books?categoryId=1",
                        "SELECT id FROM books WHERE category_id = 1"),
                Arguments.of("/books?search=la&languageId=1",
                        "SELECT id FROM books WHERE (title ILIKE '%la%' OR isbn ILIKE '%la%') AND language_id = 1"),
                Arguments.of("/categories?level=SUPERIOR",
                        "SELECT id FROM categories WHERE level = 'SUPERIOR'"),
                Arguments.of("/publishers?status=ACTIVO",
                        "SELECT id FROM publishers WHERE status = 'ACTIVO'"),
                Arguments.of("/copies?status=DISPONIBLE&condition=BUENO",
                        "SELECT id FROM copies WHERE status = 'DISPONIBLE' AND condition = 'BUENO'"),
                Arguments.of("/copies?bookId=1",
                        "SELECT id FROM copies WHERE book_id = 1"),
                Arguments.of("/locations?search=sala",
                        "SELECT id FROM locations WHERE name ILIKE '%sala%'"),
                Arguments.of("/readers?type=ESTUDIANTE&status=ACTIVO",
                        "SELECT id FROM readers WHERE type = 'ESTUDIANTE' AND status = 'ACTIVO'"),
                Arguments.of("/readers?search=maría",
                        """
                        SELECT id FROM readers WHERE code ILIKE '%maría%' OR dni ILIKE '%maría%' OR email ILIKE '%maría%'
                           OR (first_name || ' ' || last_name) ILIKE '%maría%'"""),
                Arguments.of("/reservations?status=PENDIENTE",
                        "SELECT id FROM reservations WHERE status = 'PENDIENTE'"),
                Arguments.of("/reservations?from=2024-07-25&to=2024-08-01",
                        "SELECT id FROM reservations WHERE reservation_date BETWEEN '2024-07-25' AND '2024-08-01'"),
                Arguments.of("/fines?status=PENDIENTE",
                        "SELECT id FROM fines WHERE status = 'PENDIENTE'"),
                Arguments.of("/payments?method=EFECTIVO",
                        "SELECT id FROM payments WHERE method = 'EFECTIVO'"),
                Arguments.of("/roles?search=bib",
                        "SELECT id FROM roles WHERE name ILIKE '%bib%'"),
                Arguments.of("/workers?roleId=2&status=ACTIVO",
                        "SELECT id FROM workers WHERE role_id = 2 AND status = 'ACTIVO' AND id <> 1"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("filters")
    void returnsExactlyTheMatchingRows(String uri, String sql) throws Exception {
        List<Long> expected = jdbc.sql("SELECT id FROM (" + sql + ") matched ORDER BY id DESC").query(Long.class).list();
        assertThat(expected).as("the seed must contain matching rows").isNotEmpty();

        MvcTestResult result = mvc.get().uri(uri + "&size=100").exchange();

        assertThat(result).hasStatusOk();
        List<Number> ids = JsonPath.read(result.getResponse().getContentAsString(), "$.data.content[*].id");
        assertThat(ids.stream().map(Number::longValue).toList()).isEqualTo(expected);
    }

    @Test
    void searchTreatsWildcardsLiterally() {
        assertThat(mvc.get().uri("/authors?search=%25"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.data.totalElements").isEqualTo(0);
    }

    @Test
    void rejectsUnknownEnumValues() {
        assertThat(mvc.get().uri("/books?status=PERDIDO")).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void rejectsSortingByUnlistedProperties() {
        assertThat(mvc.get().uri("/readers?sort=dni"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.message").isEqualTo("Cannot sort by 'dni'. Allowed: [code, id, lastName]");
    }

    @Test
    void rejectsReversedDateRanges() {
        assertThat(mvc.get().uri("/payments?from=2026-02-01&to=2026-01-01")).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void sortsByAllowedProperty() throws Exception {
        MvcTestResult result = mvc.get().uri("/books?sort=title,asc&size=100").exchange();

        List<String> titles = JsonPath.read(result.getResponse().getContentAsString(), "$.data.content[*].title");
        List<String> expected = jdbc.sql("SELECT title FROM books ORDER BY title, id DESC LIMIT 100").query(String.class).list();
        assertThat(titles).isEqualTo(expected);
    }
}
