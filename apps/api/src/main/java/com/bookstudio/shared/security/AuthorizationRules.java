package com.bookstudio.shared.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

/**
 * A module's request authorization rules. Each module declares its own as a
 * bean next to its controllers; {@link SecurityConfig} applies them all before
 * the catch-all "authenticated" rule.
 *
 * <p>Rules run in the filter chain, before the request body is read, so a caller
 * without the permission gets 403 and never sees validation errors.
 */
@FunctionalInterface
public interface AuthorizationRules {

    void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth);
}
