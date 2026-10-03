package com.bookstudio.staff.worker.application.dto.request;

import com.bookstudio.staff.worker.domain.model.type.WorkerStatus;

import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * Optional filters for the worker list. Absent values do not filter.
 */
public record WorkerFilter(
    @Parameter(description = "Username, email or full name contains this text (case-insensitive)")
    @Size(max = 100, message = "Search must not exceed 100 characters")
    String search,

    @Parameter(description = "Workers with this role")
    @Min(value = 1, message = "Role ID must be at least 1")
    Long roleId,

    @Parameter(description = "Status")
    WorkerStatus status
) {
}
