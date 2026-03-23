package com.bookstudio.loan.application.dto.request;

import java.util.List;

import com.bookstudio.shared.validation.NoNullElements;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateLoanRequest(
    @NotNull(message = "Reader ID is required")
    @Min(value = 1, message = "Reader ID must be at least 1")
    Long readerId,

    String observation,

    @NotNull(message = "Items are required")
    @NotEmpty(message = "Items cannot be empty")
    @NoNullElements(message = "Items cannot contain null elements")
    @Valid
    List<UpdateLoanItemRequest> items
) {}
