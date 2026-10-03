package com.bookstudio.billing.payment.application.dto.request;

import com.bookstudio.billing.payment.domain.model.type.PaymentMethod;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Optional filters for the payment list. Absent values do not filter.
 */
public record PaymentFilter(
    @Parameter(description = "Code contains this text (case-insensitive)")
    @Size(max = 100, message = "Search must not exceed 100 characters")
    String search,

    @Parameter(description = "Payments of this reader")
    @Min(value = 1, message = "Reader ID must be at least 1")
    Long readerId,

    @Parameter(description = "Method")
    PaymentMethod method,

    @Parameter(description = "Payment date on or after (yyyy-MM-dd)")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate from,

    @Parameter(description = "Payment date on or before (yyyy-MM-dd)")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate to
) {

    @Schema(hidden = true)
    @AssertTrue(message = "'from' must not be after 'to'")
    public boolean isDateRangeValid() {
        return from == null || to == null || !from.isAfter(to);
    }
}
