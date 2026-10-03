package com.bookstudio.security;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.bookstudio.shared.security.SecurityConfig;

/**
 * Builds staff authentications for tests.
 */
public final class StaffAuth {

    private StaffAuth() {
    }

    /**
     * Per-request authentication, for requests sent from other threads where the
     * test's security context is not visible.
     */
    public static RequestPostProcessor admin() {
        WithStaffUser defaults = Defaults.class.getAnnotation(WithStaffUser.class);
        return SecurityMockMvcRequestPostProcessors.authentication(
                token(defaults.id(), defaults.username(), defaults.permissions()));
    }

    static JwtAuthenticationToken token(long id, String username, String... permissions) {
        Jwt jwt = Jwt.withTokenValue("test-token")
                .header("alg", "HS256")
                .subject(username)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(900))
                .claim(SecurityConfig.USER_ID_CLAIM, id)
                .claim(SecurityConfig.PERMISSIONS_CLAIM, List.of(permissions))
                .build();
        return new JwtAuthenticationToken(jwt,
                Arrays.stream(permissions).map(SimpleGrantedAuthority::new).toList(), username);
    }

    @WithStaffUser
    private static final class Defaults {
    }
}
