package com.bookstudio.staff;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookstudio.IntegrationTest;
import com.jayway.jsonpath.JsonPath;

/**
 * Each request commits, as in production: the revocation triggered by a reused
 * refresh token must survive the 401 that reports it.
 */
@WithAnonymousUser
@IntegrationTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class RefreshTokenReuseCommittedIntegrationTest {

    @Autowired
    MockMvcTester mvc;

    @Autowired
    JdbcClient jdbc;

    private final Instant startedAt = Instant.now();

    @AfterEach
    void deleteIssuedTokens() {
        jdbc.sql("DELETE FROM refresh_tokens WHERE created_at >= ?")
                .param(java.sql.Timestamp.from(startedAt.minusSeconds(1))).update();
    }

    @Test
    void reuseRevokesTheRotatedTokenToo() throws Exception {
        String first = refreshTokenOf(mvc.post().uri("/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"username": "admin", "password": "BookStudio2026!"}""").exchange());
        String second = refreshTokenOf(refresh(first));

        assertThat(refresh(first)).hasStatus(HttpStatus.UNAUTHORIZED);
        assertThat(refresh(second)).hasStatus(HttpStatus.UNAUTHORIZED);
    }

    private MvcTestResult refresh(String token) {
        return mvc.post().uri("/auth/refresh").contentType(MediaType.APPLICATION_JSON).content("""
                {"refreshToken": "%s"}""".formatted(token)).exchange();
    }

    private static String refreshTokenOf(MvcTestResult result) throws Exception {
        assertThat(result).hasStatusOk();
        return JsonPath.read(result.getResponse().getContentAsString(), "$.data.refreshToken");
    }
}
