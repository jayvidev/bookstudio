package com.bookstudio.location.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateShelfRequest(
    @NotNull(message = "ID is required")
    @Min(value = 1, message = "ID must be at least 1")
    Long id,

    @NotBlank(message = "Code is required")
    @Size(max = 50, message = "Code must not exceed 50 characters")
    String code,

    @Size(max = 50, message = "Floor must not exceed 50 characters")
    String floor,

    String description
) {}
