package com.bookstudio.billing.fine.application.dto.request;

import com.bookstudio.billing.fine.FineStatus;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Optional filters for the fine list. Absent values do not filter.
 */
public record FineFilter(
    @Parameter(description = "Code contains this text (case-insensitive)")
    @Size(max = 100, message = "Search must not exceed 100 characters")
    String search,

    @Parameter(description = "Fines of this loan")
    @Min(value = 1, message = "Loan ID must be at least 1")
    Long loanId,

    @Parameter(description = "Status")
    FineStatus status,

    @Parameter(description = "Issue date on or after (yyyy-MM-dd)")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate from,

    @Parameter(description = "Issue date on or before (yyyy-MM-dd)")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate to
) {

    @Schema(hidden = true)
    @AssertTrue(message = "'from' must not be after 'to'")
    public boolean isDateRangeValid() {
        return from == null || to == null || !from.isAfter(to);
    }
}
