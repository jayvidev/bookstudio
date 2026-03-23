package com.bookstudio.publisher.application.dto.request;

import java.util.List;

import com.bookstudio.shared.type.Status;
import com.bookstudio.shared.validation.NoNullElements;
import com.bookstudio.shared.validation.ValidEnum;
import com.bookstudio.shared.validation.ValidIds;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePublisherRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    String name,

    @NotNull(message = "Nationality ID is required")
    @Min(value = 1, message = "Nationality ID must be at least 1")
    Long nationalityId,

    @NotNull(message = "Foundation year is required")
    @Min(value = 1400, message = "Foundation year must be at least 1400")
    Integer foundationYear,

    @Size(max = 255, message = "Website must not exceed 255 characters")
    String website,

    @Size(max = 255, message = "Address must not exceed 255 characters")
    String address,

    @NotBlank(message = "Status is required")
    @ValidEnum(enumClass = Status.class, message = "Invalid status")
    String status,

    @Size(max = 1024, message = "Photo URL must not exceed 1024 characters")
    String photoUrl,

    @NotEmpty(message = "Genre IDs cannot be empty")
    @NoNullElements(message = "Genre IDs cannot contain null values")
    @ValidIds(message = "Genre IDs must be greater than or equal to 1")
    List<Long> genreIds
) {}
