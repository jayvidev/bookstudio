package com.bookstudio.shared.security;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Settings for the access tokens this API issues and accepts.
 *
 * @param secret HMAC-SHA256 key; at least 32 bytes, never committed for production
 * @param issuer value of the {@code iss} claim, checked on every request
 * @param accessTokenTtl lifetime of an access token (short: it cannot be revoked)
 * @param refreshTokenTtl lifetime of a refresh token (revocable, rotated on use)
 */
@Validated
@ConfigurationProperties("app.security.jwt")
public record JwtProperties(
    @NotBlank @Size(min = 32, message = "must be at least 32 characters") String secret,
    @NotBlank String issuer,
    @NotNull Duration accessTokenTtl,
    @NotNull Duration refreshTokenTtl
) {
}
