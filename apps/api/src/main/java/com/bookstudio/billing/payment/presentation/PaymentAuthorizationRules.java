package com.bookstudio.billing.payment.presentation;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

import com.bookstudio.shared.security.AuthorizationRules;

@Component
class PaymentAuthorizationRules implements AuthorizationRules {

    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers(HttpMethod.POST, "/payments").hasAuthority("FINE_PAYMENT")
                .requestMatchers(HttpMethod.PUT, "/payments/*").hasAuthority("FINE_PAYMENT");
    }
}
