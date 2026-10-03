package com.bookstudio.catalog.author.application.dto.request;

import com.bookstudio.shared.type.Status;

import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * Optional filters for the author list. Absent values do not filter.
 */
public record AuthorFilter(
    @Parameter(description = "Name contains this text (case-insensitive)")
    @Size(max = 100, message = "Search must not exceed 100 characters")
    String search,

    @Parameter(description = "Authors of this nationality")
    @Min(value = 1, message = "Nationality ID must be at least 1")
    Long nationalityId,

    @Parameter(description = "Status")
    Status status
) {
}
