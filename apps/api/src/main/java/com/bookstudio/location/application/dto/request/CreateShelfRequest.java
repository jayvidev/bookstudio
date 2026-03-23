package com.bookstudio.location.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateShelfRequest(
    @NotBlank(message = "Code is required")
    @Size(max = 50, message = "Code must not exceed 50 characters")
    String code,

    @Size(max = 50, message = "Floor must not exceed 50 characters")
    String floor,

    String description
) {}
