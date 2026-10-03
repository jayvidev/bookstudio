package com.bookstudio.inventory.location.presentation;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

import com.bookstudio.shared.security.AuthorizationRules;

@Component
class LocationAuthorizationRules implements AuthorizationRules {

    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers(HttpMethod.POST, "/locations").hasAuthority("CATALOG_MANAGE")
                .requestMatchers(HttpMethod.PUT, "/locations/*").hasAuthority("CATALOG_MANAGE");
    }
}
