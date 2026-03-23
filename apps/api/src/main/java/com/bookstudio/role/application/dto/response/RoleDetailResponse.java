package com.bookstudio.role.application.dto.response;

import java.util.List;

public record RoleDetailResponse(
    Long id,
    String name,
    String description,
    List<PermissionItem> permissions
) {

    public RoleDetailResponse withPermissions(List<PermissionItem> permissions) {
        return new RoleDetailResponse(
            id,
            name,
            description,
            permissions
        );
    }

    public record PermissionItem(
        Long id,
        String code,
        String description
    ) {}
}
