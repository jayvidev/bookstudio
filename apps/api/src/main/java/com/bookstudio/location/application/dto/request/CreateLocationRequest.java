package com.bookstudio.location.application.dto.request;

import java.util.List;

import com.bookstudio.shared.validation.NoNullElements;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateLocationRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    String name,

    String description,

    @NotNull(message = "Shelves are required")
    @NotEmpty(message = "Shelves cannot be empty")
    @NoNullElements(message = "Shelves cannot contain null elements")
    @Valid
    List<CreateShelfRequest> shelves
) {}
