package com.bookstudio.circulation.loan.application.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.bookstudio.circulation.LoanItemStatus;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * Optional filters for the loan list. Absent values do not filter.
 */
public record LoanFilter(
    @Parameter(description = "Loans with at least one item in this status")
    LoanItemStatus status,

    @Parameter(description = "Loans of this reader")
    @Min(value = 1, message = "Reader ID must be at least 1")
    Long readerId,

    @Parameter(description = "Loan code or reader name contains this text (case-insensitive)")
    @Size(max = 100, message = "Search must not exceed 100 characters")
    String search,

    @Parameter(description = "Loan date on or after (yyyy-MM-dd)")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate from,

    @Parameter(description = "Loan date on or before (yyyy-MM-dd)")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate to
) {

    @Schema(hidden = true)
    @AssertTrue(message = "'from' must not be after 'to'")
    public boolean isDateRangeValid() {
        return from == null || to == null || !from.isAfter(to);
    }

    public boolean hasSearch() {
        return search != null && !search.isBlank();
    }
}
