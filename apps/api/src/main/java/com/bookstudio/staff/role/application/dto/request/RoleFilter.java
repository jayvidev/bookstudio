package com.bookstudio.staff.role.application.dto.request;

import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.constraints.Size;

/**
 * Optional filters for the role list. Absent values do not filter.
 */
public record RoleFilter(
    @Parameter(description = "Name contains this text (case-insensitive)")
    @Size(max = 100, message = "Search must not exceed 100 characters")
    String search
) {
}
