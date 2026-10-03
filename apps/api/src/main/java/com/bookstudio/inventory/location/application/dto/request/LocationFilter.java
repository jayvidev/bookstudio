package com.bookstudio.inventory.location.application.dto.request;

import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.constraints.Size;

/**
 * Optional filters for the location list. Absent values do not filter.
 */
public record LocationFilter(
    @Parameter(description = "Name contains this text (case-insensitive)")
    @Size(max = 100, message = "Search must not exceed 100 characters")
    String search
) {
}
