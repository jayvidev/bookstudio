package com.bookstudio.role.application.dto.response;

public record RoleListResponse(
    Long id,
    String name,
    String description,
    Long permissionCount
) {}
