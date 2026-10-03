package com.bookstudio.shared.security;

import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Reads the authenticated staff member from the access token.
 */
public final class CurrentUser {

    private CurrentUser() {
    }

    public static Long id(Jwt jwt) {
        Number id = jwt.getClaim(SecurityConfig.USER_ID_CLAIM);
        return id.longValue();
    }
}
