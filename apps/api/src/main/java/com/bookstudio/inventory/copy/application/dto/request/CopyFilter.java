package com.bookstudio.inventory.copy.application.dto.request;

import com.bookstudio.inventory.CopyStatus;
import com.bookstudio.inventory.copy.domain.model.type.CopyCondition;

import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * Optional filters for the copy list. Absent values do not filter.
 */
public record CopyFilter(
    @Parameter(description = "Code or barcode contains this text (case-insensitive)")
    @Size(max = 100, message = "Search must not exceed 100 characters")
    String search,

    @Parameter(description = "Copies of this book")
    @Min(value = 1, message = "Book ID must be at least 1")
    Long bookId,

    @Parameter(description = "Copies on this shelf")
    @Min(value = 1, message = "Shelf ID must be at least 1")
    Long shelfId,

    @Parameter(description = "Status")
    CopyStatus status,

    @Parameter(description = "Condition")
    CopyCondition condition
) {
}
