package com.bookstudio;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

/**
 * Client errors must never surface as 500.
 */
@IntegrationTest
class ErrorStatusIntegrationTest {

    @Autowired
    MockMvcTester mvc;

    @Autowired
    JdbcClient jdbc;

    @Test
    void unknownRoleIs404() {
        assertThat(mvc.get().uri("/roles/999999"))
                .hasStatus(HttpStatus.NOT_FOUND)
                .bodyJson()
                .extractingPath("$.message").isEqualTo("Role not found with ID: 999999");
    }

    @Test
    void duplicateReaderDniIs409() {
        String dni = jdbc.sql("SELECT dni FROM readers ORDER BY id LIMIT 1").query(String.class).single();

        assertThat(mvc.post().uri("/readers").contentType(MediaType.APPLICATION_JSON).content("""
                {"dni": "%s", "firstName": "Dup", "lastName": "Licate", "address": "Lima", "phone": "999999999",
                 "email": "dup@test.pe", "birthDate": "2000-01-01", "gender": "FEMENINO", "type": "ESTUDIANTE",
                 "status": "ACTIVO"}""".formatted(dni)))
                .hasStatus(HttpStatus.CONFLICT)
                .bodyJson()
                .extractingPath("$.message").isEqualTo("The provided DNI is already registered.");
    }

    @Test
    void duplicateWorkerUsernameIs409() {
        String username = jdbc.sql("SELECT username FROM workers ORDER BY id LIMIT 1").query(String.class).single();

        assertThat(mvc.post().uri("/workers").contentType(MediaType.APPLICATION_JSON).content("""
                {"username": "%s", "email": "new-worker@bookstudio.pe", "firstName": "Dup", "lastName": "Licate",
                 "password": "Secret123!", "roleId": 1, "profilePhotoUrl": null, "status": "ACTIVO"}""".formatted(username)))
                .hasStatus(HttpStatus.CONFLICT)
                .bodyJson()
                .extractingPath("$.message").isEqualTo("The provided username is already registered.");
    }
}
