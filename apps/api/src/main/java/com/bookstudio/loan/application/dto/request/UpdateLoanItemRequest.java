package com.bookstudio.loan.application.dto.request;

import java.time.LocalDate;

import com.bookstudio.loan.domain.model.type.LoanItemStatus;
import com.bookstudio.shared.validation.ValidEnum;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateLoanItemRequest(
    @NotNull(message = "Copy ID is required")
    @Min(value = 1, message = "Copy ID must be at least 1")
    Long copyId,

    @NotNull(message = "Due date is required")
    @Future(message = "Due date must be in the future")
    LocalDate dueDate,

    @NotBlank(message = "Status is required")
    @ValidEnum(enumClass = LoanItemStatus.class, message = "Invalid status")
    String status
) {}
