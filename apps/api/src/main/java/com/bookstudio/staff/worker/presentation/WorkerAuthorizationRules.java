package com.bookstudio.staff.worker.presentation;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

import com.bookstudio.shared.security.AuthorizationRules;

@Component
class WorkerAuthorizationRules implements AuthorizationRules {

    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers(HttpMethod.POST, "/workers").hasAuthority("USER_CREATE")
                .requestMatchers(HttpMethod.PUT, "/workers/*").hasAuthority("USER_EDIT")
                .requestMatchers(HttpMethod.GET, "/workers", "/workers/**").hasAnyAuthority("USER_CREATE", "USER_EDIT");
    }
}
