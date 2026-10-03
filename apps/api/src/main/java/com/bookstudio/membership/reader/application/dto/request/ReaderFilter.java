package com.bookstudio.membership.reader.application.dto.request;

import com.bookstudio.membership.reader.domain.model.type.ReaderStatus;
import com.bookstudio.membership.reader.domain.model.type.ReaderType;

import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.constraints.Size;

/**
 * Optional filters for the reader list. Absent values do not filter.
 */
public record ReaderFilter(
    @Parameter(description = "Code, DNI, email or full name contains this text (case-insensitive)")
    @Size(max = 100, message = "Search must not exceed 100 characters")
    String search,

    @Parameter(description = "Type")
    ReaderType type,

    @Parameter(description = "Status")
    ReaderStatus status
) {
}
