package com.bookstudio.worker.application.dto.request;

import com.bookstudio.worker.domain.model.type.WorkerStatus;
import com.bookstudio.shared.validation.ValidEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateWorkerRequest(
    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username must not exceed 50 characters")
    String username,

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    String email,

    @NotBlank(message = "First name is required")
    @Size(max = 255, message = "First name must not exceed 255 characters")
    String firstName,

    @NotBlank(message = "Last name is required")
    @Size(max = 255, message = "Last name must not exceed 255 characters")
    String lastName,

    @NotBlank(message = "Password is required")
    @Size(max = 255, message = "Password must not exceed 255 characters")
    String password,

    @NotNull(message = "Role ID is required")
    @Min(value = 1, message = "Role ID must be at least 1")
    Long roleId,

    @Size(max = 1024, message = "Profile photo URL must not exceed 1024 characters")
    String profilePhotoUrl,

    @NotBlank(message = "Status is required")
    @ValidEnum(enumClass = WorkerStatus.class, message = "Invalid status")
    String status
) {}
