package com.bookstudio.staff.auth.application;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.bookstudio.shared.security.JwtProperties;
import com.bookstudio.shared.security.SecurityConfig;

import lombok.RequiredArgsConstructor;

/**
 * Issues signed access tokens and random refresh tokens.
 */
@Service
@RequiredArgsConstructor
class TokenService {
    private static final SecureRandom RANDOM = new SecureRandom();

    private final JwtEncoder jwtEncoder;
    private final JwtProperties properties;

    String issueAccessToken(Long workerId, String username, String role, List<String> permissions, Instant now) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(properties.issuer())
                .subject(username)
                .issuedAt(now)
                .expiresAt(now.plus(properties.accessTokenTtl()))
                .claim(SecurityConfig.USER_ID_CLAIM, workerId)
                .claim(SecurityConfig.ROLE_CLAIM, role)
                .claim(SecurityConfig.PERMISSIONS_CLAIM, permissions)
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    /**
     * 256 random bits, URL-safe. Only its hash is stored.
     */
    String newRefreshToken() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    String hash(String refreshToken) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(refreshToken.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }

    long accessTokenTtlSeconds() {
        return properties.accessTokenTtl().toSeconds();
    }

    Instant refreshTokenExpiry(Instant now) {
        return now.plus(properties.refreshTokenTtl());
    }
}
