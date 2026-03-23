package com.bookstudio.author.application.dto.request;

import java.time.LocalDate;

import com.bookstudio.shared.type.Status;
import com.bookstudio.shared.validation.ValidEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record UpdateAuthorRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    String name,

    @NotNull(message = "Nationality ID cannot be null")
    @Min(value = 1, message = "Nationality ID must be at least 1")
    Long nationalityId,
    
    @NotNull(message = "Birth date is required")
    @PastOrPresent(message = "Birth date must be in the past or present")
    LocalDate birthDate,

    String biography,

    @NotBlank(message = "Status is required")
    @ValidEnum(enumClass = Status.class, message = "Invalid status")
    String status,
    
    @Size(max = 1024, message = "Photo URL must not exceed 1024 characters")
    String photoUrl
) {}
