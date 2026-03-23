package com.bookstudio.copy.application.dto.request;

import com.bookstudio.copy.domain.model.type.CopyCondition;
import com.bookstudio.copy.domain.model.type.CopyStatus;
import com.bookstudio.shared.validation.ValidEnum;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCopyRequest(
    @NotNull(message = "Book ID is required")
    @Min(value = 1, message = "Book ID must be at least 1")
    Long bookId,

    @NotNull(message = "Shelf ID is required")
    @Min(value = 1, message = "Shelf ID must be at least 1")
    Long shelfId,

    @NotBlank(message = "Barcode is required")
    @Size(max = 50, message = "Barcode must not exceed 50 characters")
    String barcode,

    @NotBlank(message = "Status is required")
    @ValidEnum(enumClass = CopyStatus.class, message = "Invalid status")
    String status,

    @NotBlank(message = "Condition is required")
    @ValidEnum(enumClass = CopyCondition.class, message = "Invalid condition")
    String condition
) {}
