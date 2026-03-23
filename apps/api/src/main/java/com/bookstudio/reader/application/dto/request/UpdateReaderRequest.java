package com.bookstudio.reader.application.dto.request;

import java.time.LocalDate;

import com.bookstudio.reader.domain.model.type.ReaderGender;
import com.bookstudio.reader.domain.model.type.ReaderStatus;
import com.bookstudio.reader.domain.model.type.ReaderType;
import com.bookstudio.shared.validation.ValidEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateReaderRequest(
    @NotBlank(message = "DNI is required")
    @Pattern(regexp = "^[0-9]{8}$", message = "DNI must be exactly 8 digits")
    String dni,

    @NotBlank(message = "First name is required")
    @Size(max = 255, message = "First name must not exceed 255 characters")
    String firstName,

    @NotBlank(message = "Last name is required")
    @Size(max = 255, message = "Last name must not exceed 255 characters")
    String lastName,

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    String address,

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{9}$", message = "Phone must be exactly 9 digits")
    String phone,

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    String email,

    @NotNull(message = "Birth date is required")
    @PastOrPresent(message = "Birth date must be in the past or present")
    LocalDate birthDate,

    @NotBlank(message = "Gender is required")
    @ValidEnum(enumClass = ReaderGender.class, message = "Invalid gender")
    String gender,

    @NotBlank(message = "Type is required")
    @ValidEnum(enumClass = ReaderType.class, message = "Invalid type")
    String type,

    @NotBlank(message = "Status is required")
    @ValidEnum(enumClass = ReaderStatus.class, message = "Invalid status")
    String status
) {}
