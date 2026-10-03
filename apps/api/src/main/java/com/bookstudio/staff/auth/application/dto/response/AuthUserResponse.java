package com.bookstudio.staff.auth.application.dto.response;

import java.util.List;

public record AuthUserResponse(
    Long id,
    String username,
    String fullName,
    String role,
    List<String> permissions
) {}
