package com.bookstudio.inventory.copy.presentation;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

import com.bookstudio.shared.security.AuthorizationRules;

@Component
class CopyAuthorizationRules implements AuthorizationRules {

    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers(HttpMethod.POST, "/copies").hasAuthority("CATALOG_MANAGE")
                .requestMatchers(HttpMethod.PUT, "/copies/*").hasAuthority("CATALOG_MANAGE");
    }
}
