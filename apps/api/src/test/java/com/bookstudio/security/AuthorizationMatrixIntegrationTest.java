package com.bookstudio.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.bookstudio.IntegrationTest;

/**
 * Which permission each write needs. A caller without it gets 403 before the
 * body is even validated; with it, the request reaches the controller.
 */
@IntegrationTest
class AuthorizationMatrixIntegrationTest {

    @Autowired
    MockMvcTester mvc;

    static Stream<Arguments> protectedRequests() {
        return Stream.of(
                Arguments.of("POST", "/books", "BOOK_CREATE"),
                Arguments.of("PUT", "/books/1", "BOOK_EDIT"),
                Arguments.of("POST", "/authors", "CATALOG_MANAGE"),
                Arguments.of("PUT", "/authors/1", "CATALOG_MANAGE"),
                Arguments.of("POST", "/publishers", "CATALOG_MANAGE"),
                Arguments.of("PUT", "/publishers/1", "CATALOG_MANAGE"),
                Arguments.of("POST", "/categories", "CATALOG_MANAGE"),
                Arguments.of("PUT", "/categories/1", "CATALOG_MANAGE"),
                Arguments.of("POST", "/copies", "CATALOG_MANAGE"),
                Arguments.of("PUT", "/copies/1", "CATALOG_MANAGE"),
                Arguments.of("POST", "/locations", "CATALOG_MANAGE"),
                Arguments.of("PUT", "/locations/1", "CATALOG_MANAGE"),
                Arguments.of("POST", "/readers", "READER_MANAGE"),
                Arguments.of("PUT", "/readers/1", "READER_MANAGE"),
                Arguments.of("POST", "/loans", "LOAN_CREATE"),
                Arguments.of("PUT", "/loans/1", "LOAN_EDIT"),
                Arguments.of("POST", "/reservations", "RESERVATION_MANAGE"),
                Arguments.of("PUT", "/reservations/1", "RESERVATION_MANAGE"),
                Arguments.of("POST", "/fines", "FINE_CREATE"),
                Arguments.of("PUT", "/fines/1", "FINE_EDIT"),
                Arguments.of("POST", "/payments", "FINE_PAYMENT"),
                Arguments.of("PUT", "/payments/1", "FINE_PAYMENT"),
                Arguments.of("POST", "/workers", "USER_CREATE"),
                Arguments.of("PUT", "/workers/1", "USER_EDIT"),
                Arguments.of("GET", "/workers", "USER_EDIT"),
                Arguments.of("POST", "/roles", "ADMIN_FULL"),
                Arguments.of("PUT", "/roles/1", "ADMIN_FULL"),
                Arguments.of("GET", "/roles", "USER_CREATE"));
    }

    @ParameterizedTest(name = "{0} {1} without {2} -> 403")
    @MethodSource("protectedRequests")
    void deniesWithoutThePermission(String method, String uri, String permission) {
        assertThat(send(method, uri, StaffAuth.withPermissions("REPORT_VIEW")))
                .hasStatus(HttpStatus.FORBIDDEN)
                .bodyJson()
                .extractingPath("$.message").isEqualTo("Access denied");
    }

    @ParameterizedTest(name = "{0} {1} with {2} -> allowed")
    @MethodSource("protectedRequests")
    void allowsWithThePermission(String method, String uri, String permission) {
        MvcTestResult result = send(method, uri, StaffAuth.withPermissions(permission));

        // An empty body fails validation (400): what matters is that authorization let it through.
        assertThat(result.getResponse().getStatus()).isNotIn(401, 403);
    }

    @ParameterizedTest(name = "GET {0} needs no permission")
    @MethodSource("readsOpenToAnyStaff")
    void readsOnlyNeedAuthentication(String uri) {
        assertThat(send("GET", uri, StaffAuth.withPermissions())).hasStatusOk();
    }

    static Stream<String> readsOpenToAnyStaff() {
        return Stream.of("/loans", "/books", "/copies", "/readers", "/fines", "/payments", "/reservations", "/authors");
    }

    private MvcTestResult send(String method, String uri, RequestPostProcessor auth) {
        return mvc.perform(MockMvcRequestBuilders.request(HttpMethod.valueOf(method), uri)
                .with(auth)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));
    }
}
