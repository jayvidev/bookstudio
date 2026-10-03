package com.bookstudio.circulation.reservation.application.dto.request;

import com.bookstudio.circulation.reservation.domain.model.type.ReservationStatus;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Optional filters for the reservation list. Absent values do not filter.
 */
public record ReservationFilter(
    @Parameter(description = "Code contains this text (case-insensitive)")
    @Size(max = 100, message = "Search must not exceed 100 characters")
    String search,

    @Parameter(description = "Reservations of this reader")
    @Min(value = 1, message = "Reader ID must be at least 1")
    Long readerId,

    @Parameter(description = "Reservations of this copy")
    @Min(value = 1, message = "Copy ID must be at least 1")
    Long copyId,

    @Parameter(description = "Status")
    ReservationStatus status,

    @Parameter(description = "Reservation date on or after (yyyy-MM-dd)")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate from,

    @Parameter(description = "Reservation date on or before (yyyy-MM-dd)")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate to
) {

    @Schema(hidden = true)
    @AssertTrue(message = "'from' must not be after 'to'")
    public boolean isDateRangeValid() {
        return from == null || to == null || !from.isAfter(to);
    }
}
