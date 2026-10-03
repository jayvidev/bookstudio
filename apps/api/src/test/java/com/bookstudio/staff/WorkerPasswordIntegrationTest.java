package com.bookstudio.staff;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.bookstudio.IntegrationTest;

@IntegrationTest
class WorkerPasswordIntegrationTest {

    @Autowired
    MockMvcTester mvc;

    @Autowired
    JdbcClient jdbc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void storesOnlyABcryptHashOfThePassword() {
        assertThat(mvc.post().uri("/workers").contentType(MediaType.APPLICATION_JSON).content("""
                {"username": "hashed", "email": "hashed@bookstudio.pe", "firstName": "Hash", "lastName": "Ed",
                 "password": "Secret123!", "roleId": 1, "profilePhotoUrl": null, "status": "ACTIVO"}"""))
                .hasStatus(HttpStatus.CREATED);

        String stored = jdbc.sql("SELECT password FROM workers WHERE username = 'hashed'").query(String.class).single();
        assertThat(stored).startsWith("$2a$12$").doesNotContain("Secret123!");
        assertThat(passwordEncoder.matches("Secret123!", stored)).isTrue();
    }

    @Test
    void seededDemoAccountsUseTheDocumentedPassword() {
        String stored = jdbc.sql("SELECT password FROM workers WHERE username = 'demo'").query(String.class).single();

        assertThat(passwordEncoder.matches("BookStudio2026!", stored)).isTrue();
    }

    @Test
    void everyEnvironmentHasRolesAndPermissions() {
        assertThat(jdbc.sql("""
                SELECT COUNT(*) FROM role_permissions rp
                JOIN roles r ON r.id = rp.role_id JOIN permissions p ON p.id = rp.permission_id
                WHERE r.name = 'Asistente' AND p.code = 'READER_MANAGE'""").query(Long.class).single()).isEqualTo(1);
    }
}
