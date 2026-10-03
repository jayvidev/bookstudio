package com.bookstudio.inventory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import com.bookstudio.IntegrationTest;

/**
 * Loans own the PRESTADO status: editing a copy cannot lend it or take it off loan.
 */
@IntegrationTest
class CopyStatusGuardIntegrationTest {

    @Autowired
    MockMvcTester mvc;

    @Autowired
    JdbcClient jdbc;

    @Test
    void aCopyOnLoanCannotBeEditedOffLoan() {
        assertThat(update(copyWithStatus("PRESTADO"), "DISPONIBLE"))
                .hasStatus(HttpStatus.CONFLICT)
                .bodyJson()
                .extractingPath("$.message").asString().endsWith("is on loan; change it through its loan");
    }

    @Test
    void editingCannotPutACopyOnLoan() {
        assertThat(update(copyWithStatus("DISPONIBLE"), "PRESTADO")).hasStatus(HttpStatus.CONFLICT);
    }

    @Test
    void otherStatusChangesAreAllowed() {
        Map<String, Object> copy = copyWithStatus("DISPONIBLE");

        assertThat(update(copy, "MANTENIMIENTO")).hasStatusOk();
        assertThat(jdbc.sql("SELECT status FROM copies WHERE id = ?").param(copy.get("id"))
                .query(String.class).single()).isEqualTo("MANTENIMIENTO");
    }

    @Test
    void aNewCopyCannotStartOnLoan() {
        assertThat(mvc.post().uri("/copies").contentType(MediaType.APPLICATION_JSON).content("""
                {"bookId": 1, "shelfId": 1, "barcode": "GUARD-0001", "status": "PRESTADO", "condition": "NUEVO"}"""))
                .hasStatus(HttpStatus.CONFLICT);
    }

    private Map<String, Object> copyWithStatus(String status) {
        return jdbc.sql("SELECT id, shelf_id, barcode FROM copies WHERE status = ? ORDER BY id LIMIT 1")
                .param(status).query().singleRow();
    }

    private MvcTestResult update(Map<String, Object> copy, String status) {
        return mvc.put().uri("/copies/" + copy.get("id")).contentType(MediaType.APPLICATION_JSON).content("""
                {"shelfId": %s, "barcode": "%s", "status": "%s", "condition": "BUENO"}"""
                .formatted(copy.get("shelf_id"), copy.get("barcode"), status)).exchange();
    }
}
