package com.bookstudio.staff.role.presentation;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

import com.bookstudio.shared.security.AuthorizationRules;

@Component
class RoleAuthorizationRules implements AuthorizationRules {

    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers(HttpMethod.POST, "/roles").hasAuthority("ADMIN_FULL")
                .requestMatchers(HttpMethod.PUT, "/roles/*").hasAuthority("ADMIN_FULL")
                .requestMatchers(HttpMethod.GET, "/roles", "/roles/**").hasAnyAuthority("USER_CREATE", "USER_EDIT");
    }
}
