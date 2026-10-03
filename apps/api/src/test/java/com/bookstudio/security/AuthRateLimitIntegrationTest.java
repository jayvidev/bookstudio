package com.bookstudio.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import com.bookstudio.IntegrationTest;

@WithAnonymousUser
@IntegrationTest
@TestPropertySource(properties = "app.security.auth-rate-limit.requests-per-minute=3")
class AuthRateLimitIntegrationTest {

    @Autowired
    MockMvcTester mvc;

    @Test
    void throttlesRepeatedLoginAttemptsFromTheSameAddress() {
        for (int i = 0; i < 3; i++) {
            assertThat(login("10.0.0.1")).hasStatus(HttpStatus.UNAUTHORIZED);
        }

        assertThat(login("10.0.0.1"))
                .hasStatus(HttpStatus.TOO_MANY_REQUESTS)
                .hasHeader("Retry-After", "60")
                .bodyJson()
                .extractingPath("$.message").isEqualTo("Too many attempts. Try again in a minute.");

        assertThat(login("10.0.0.2")).hasStatus(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void ignoresSpoofedForwardedForHeaders() {
        for (int i = 0; i < 3; i++) {
            assertThat(login("10.0.0.3")).hasStatus(HttpStatus.UNAUTHORIZED);
        }

        assertThat(mvc.post().uri("/auth/login")
                .with(request -> {
                    request.setRemoteAddr("10.0.0.3");
                    request.addHeader("X-Forwarded-For", "203.0.113.99");
                    return request;
                })
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"username": "admin", "password": "wrong"}"""))
                .hasStatus(HttpStatus.TOO_MANY_REQUESTS);
    }

    private MvcTestResult login(String ip) {
        return mvc.post().uri("/auth/login")
                .with(request -> {
                    request.setRemoteAddr(ip);
                    return request;
                })
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"username": "admin", "password": "wrong"}""")
                .exchange();
    }
}
