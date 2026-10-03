package com.bookstudio.catalog.book.application.dto.request;

import com.bookstudio.shared.type.Status;

import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * Optional filters for the book list. Absent values do not filter.
 */
public record BookFilter(
    @Parameter(description = "Title or ISBN contains this text (case-insensitive)")
    @Size(max = 100, message = "Search must not exceed 100 characters")
    String search,

    @Parameter(description = "Books in this category")
    @Min(value = 1, message = "Category ID must be at least 1")
    Long categoryId,

    @Parameter(description = "Books from this publisher")
    @Min(value = 1, message = "Publisher ID must be at least 1")
    Long publisherId,

    @Parameter(description = "Books in this language")
    @Min(value = 1, message = "Language ID must be at least 1")
    Long languageId,

    @Parameter(description = "Status")
    Status status
) {
}
