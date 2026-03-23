package com.bookstudio.book.application.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.bookstudio.shared.type.Status;
import com.bookstudio.shared.validation.NoNullElements;
import com.bookstudio.shared.validation.ValidIds;
import com.bookstudio.shared.validation.ValidEnum;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record CreateBookRequest(
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    String title,

    @Size(max = 20, message = "ISBN must not exceed 20 characters")
    String isbn,

    @NotNull(message = "Language ID is required")
    @Min(value = 1, message = "Language ID must be at least 1")
    Long languageId,

    @Size(max = 50, message = "Edition must not exceed 50 characters")
    String edition,

    @Min(value = 1, message = "Pages must be at least 1")
    Integer pages,

    String description,

    @Size(max = 300, message = "Cover URL must not exceed 300 characters")
    String coverUrl,

    @NotNull(message = "Publisher ID is required")
    @Min(value = 1, message = "Publisher ID must be at least 1")
    Long publisherId,

    @NotNull(message = "Category ID is required")
    @Min(value = 1, message = "Category ID must be at least 1")
    Long categoryId,

    @NotNull(message = "Release date is required")
    @PastOrPresent(message = "Release date must be in the past or present")
    LocalDate releaseDate,

    @NotBlank(message = "Status is required")
    @ValidEnum(enumClass = Status.class, message = "Invalid status")
    String status,

    @NotEmpty(message = "Author IDs cannot be empty")
    @NoNullElements(message = "Author IDs cannot contain null values")
    @ValidIds(message = "Author IDs must be greater than or equal to 1")
    List<Long> authorIds,

    @NotEmpty(message = "Genre IDs cannot be empty")
    @NoNullElements(message = "Genre IDs cannot contain null values")
    @ValidIds(message = "Genre IDs must be greater than or equal to 1")
    List<Long> genreIds
) {}
