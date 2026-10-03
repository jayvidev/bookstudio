package com.bookstudio.catalog.category.application.dto.request;

import com.bookstudio.catalog.category.domain.model.type.CategoryLevel;
import com.bookstudio.shared.type.Status;

import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.constraints.Size;

/**
 * Optional filters for the category list. Absent values do not filter.
 */
public record CategoryFilter(
    @Parameter(description = "Name contains this text (case-insensitive)")
    @Size(max = 100, message = "Search must not exceed 100 characters")
    String search,

    @Parameter(description = "Level")
    CategoryLevel level,

    @Parameter(description = "Status")
    Status status
) {
}
