package com.bookstudio.category.application.dto.request;

import com.bookstudio.category.domain.model.type.CategoryLevel;
import com.bookstudio.shared.type.Status;
import com.bookstudio.shared.validation.ValidEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    String name,

    @NotBlank(message = "Level is required")
    @ValidEnum(enumClass = CategoryLevel.class, message = "Invalid level")
    String level,

    String description,

    @NotBlank(message = "Status is required")
    @ValidEnum(enumClass = Status.class, message = "Invalid status")
    String status
) {}
