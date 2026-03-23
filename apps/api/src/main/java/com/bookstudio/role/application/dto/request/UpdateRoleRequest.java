package com.bookstudio.role.application.dto.request;

import java.util.List;

import com.bookstudio.shared.validation.NoNullElements;
import com.bookstudio.shared.validation.ValidIds;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdateRoleRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    String name,

    String description,

    @NotEmpty(message = "Permission IDs cannot be empty")
    @NoNullElements(message = "Permission IDs cannot contain null values")
    @ValidIds(message = "Permission IDs must be greater than or equal to 1")
    List<Long> permissionIds
) {}
