package com.bookstudio.staff;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import com.bookstudio.IntegrationTest;
import com.jayway.jsonpath.JsonPath;

/**
 * Real tokens end to end: no mocked security context.
 */
@WithAnonymousUser
@IntegrationTest
class AuthIntegrationTest {

    private static final String DEMO_PASSWORD = "BookStudio2026!";

    @Autowired
    MockMvcTester mvc;

    @Test
    void protectedEndpointsRequireAToken() {
        assertThat(mvc.get().uri("/loans"))
                .hasStatus(HttpStatus.UNAUTHORIZED)
                .hasHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer")
                .bodyJson()
                .satisfies(json -> {
                    json.assertThat().extractingPath("$.success").isEqualTo(false);
                    json.assertThat().extractingPath("$.status").isEqualTo(401);
                    json.assertThat().extractingPath("$.message").isEqualTo("Authentication required");
                });
    }

    @Test
    void rejectsATamperedToken() throws Exception {
        String token = login("admin", DEMO_PASSWORD).accessToken();
        String tampered = token.substring(0, token.length() - 4) + "AAAA";

        assertThat(mvc.get().uri("/loans").header(HttpHeaders.AUTHORIZATION, "Bearer " + tampered))
                .hasStatus(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void healthStaysPublic() {
        assertThat(mvc.get().uri("/actuator/health")).hasStatusOk();
    }

    @Test
    void loginReturnsTokensThatAuthenticateRequests() throws Exception {
        Tokens tokens = login("admin", DEMO_PASSWORD);

        assertThat(mvc.get().uri("/loans").header(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.accessToken()))
                .hasStatusOk();
    }

    @Test
    void loginReturnsTheUserWithRoleAndPermissions() {
        assertThat(postLogin("asistente1", DEMO_PASSWORD))
                .hasStatusOk()
                .bodyJson()
                .satisfies(json -> {
                    json.assertThat().extractingPath("$.data.tokenType").isEqualTo("Bearer");
                    json.assertThat().extractingPath("$.data.expiresIn").isEqualTo(900);
                    json.assertThat().extractingPath("$.data.user.username").isEqualTo("asistente1");
                    json.assertThat().extractingPath("$.data.user.role").isEqualTo("Asistente");
                    json.assertThat().extractingPath("$.data.user.permissions").asArray()
                            .containsExactly("LOAN_CREATE", "LOAN_RETURN", "READER_MANAGE", "REPORT_VIEW", "RESERVATION_MANAGE");
                });
    }

    @Test
    void wrongPasswordUnknownUserAndSuspendedAccountLookTheSame() {
        for (var credentials : List.of(List.of("admin", "wrong"), List.of("nobody", DEMO_PASSWORD),
                List.of("bibliotecario5", DEMO_PASSWORD))) {
            assertThat(postLogin(credentials.get(0), credentials.get(1)))
                    .hasStatus(HttpStatus.UNAUTHORIZED)
                    .bodyJson()
                    .extractingPath("$.message").isEqualTo("Invalid username or password");
        }
    }

    @Test
    void meDescribesTheTokenOwner() throws Exception {
        String token = login("bibliotecario1", DEMO_PASSWORD).accessToken();

        assertThat(mvc.get().uri("/auth/me").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .hasStatusOk()
                .bodyJson()
                .satisfies(json -> {
                    json.assertThat().extractingPath("$.data.username").isEqualTo("bibliotecario1");
                    json.assertThat().extractingPath("$.data.role").isEqualTo("Bibliotecario");
                });
    }

    @Test
    void refreshRotatesTheRefreshToken() throws Exception {
        Tokens first = login("admin", DEMO_PASSWORD);

        Tokens second = tokens(refresh(first.refreshToken()));

        assertThat(second.refreshToken()).isNotEqualTo(first.refreshToken());
        assertThat(mvc.get().uri("/loans").header(HttpHeaders.AUTHORIZATION, "Bearer " + second.accessToken()))
                .hasStatusOk();
    }

    @Test
    void reusingARefreshTokenRevokesTheWholeSession() throws Exception {
        Tokens first = login("admin", DEMO_PASSWORD);
        Tokens second = tokens(refresh(first.refreshToken()));

        assertThat(refresh(first.refreshToken())).hasStatus(HttpStatus.UNAUTHORIZED);
        assertThat(refresh(second.refreshToken())).hasStatus(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void logoutRevokesTheRefreshToken() throws Exception {
        Tokens tokens = login("admin", DEMO_PASSWORD);

        assertThat(mvc.post().uri("/auth/logout")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"refreshToken": "%s"}""".formatted(tokens.refreshToken())))
                .hasStatus(HttpStatus.NO_CONTENT);

        assertThat(refresh(tokens.refreshToken())).hasStatus(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void demoLoginSignsInAsTheDemoAccount() {
        assertThat(mvc.post().uri("/auth/demo"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.data.user.username").isEqualTo("demo");
    }

    private MvcTestResult postLogin(String username, String password) {
        return mvc.post().uri("/auth/login").contentType(MediaType.APPLICATION_JSON).content("""
                {"username": "%s", "password": "%s"}""".formatted(username, password)).exchange();
    }

    private MvcTestResult refresh(String refreshToken) {
        return mvc.post().uri("/auth/refresh").contentType(MediaType.APPLICATION_JSON).content("""
                {"refreshToken": "%s"}""".formatted(refreshToken)).exchange();
    }

    private Tokens login(String username, String password) throws Exception {
        return tokens(postLogin(username, password));
    }

    private static Tokens tokens(MvcTestResult result) throws Exception {
        assertThat(result).hasStatusOk();
        String body = result.getResponse().getContentAsString();
        return new Tokens(JsonPath.read(body, "$.data.accessToken"), JsonPath.read(body, "$.data.refreshToken"));
    }

    private record Tokens(String accessToken, String refreshToken) {
    }
}
