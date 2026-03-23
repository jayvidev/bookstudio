package com.bookstudio.reservation.application.dto.request;

import java.time.LocalDate;

import com.bookstudio.reservation.domain.model.type.ReservationStatus;
import com.bookstudio.shared.validation.ValidEnum;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record UpdateReservationRequest(
    @NotNull(message = "Reader ID is required")
    @Min(value = 1, message = "Reader ID must be at least 1")
    Long readerId,

    @NotNull(message = "Copy ID is required")
    @Min(value = 1, message = "Copy ID must be at least 1")
    Long copyId,

    @NotNull(message = "Reservation date is required")
    @PastOrPresent(message = "Reservation date must be in the past or present")
    LocalDate reservationDate,

    @NotBlank(message = "Status is required")
    @ValidEnum(enumClass = ReservationStatus.class, message = "Invalid status")
    String status
) {}
