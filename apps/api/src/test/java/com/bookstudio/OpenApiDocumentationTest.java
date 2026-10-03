package com.bookstudio;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WithAnonymousUser
@IntegrationTest
class OpenApiDocumentationTest {

    @Autowired
    MockMvcTester mvc;

    @Test
    void documentsBearerAuthenticationAndPublicAuthEndpoints() {
        assertThat(mvc.get().uri("/v3/api-docs"))
                .hasStatusOk()
                .bodyJson()
                .satisfies(json -> {
                    json.assertThat().extractingPath("$.components.securitySchemes.bearerAuth.scheme").isEqualTo("bearer");
                    json.assertThat().extractingPath("$.security[0].bearerAuth").isNotNull();
                    json.assertThat().extractingPath("$.paths['/auth/login'].post.security").asArray().isEmpty();
                });
    }
}
