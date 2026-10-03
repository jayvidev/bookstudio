package com.bookstudio.staff.auth.application.dto.response;

public record AuthResponse(
    String accessToken,
    String tokenType,
    long expiresIn,
    String refreshToken,
    AuthUserResponse user
) {}
