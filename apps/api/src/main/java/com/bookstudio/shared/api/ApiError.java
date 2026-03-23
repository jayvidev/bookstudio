package com.bookstudio.shared.api;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonPropertyOrder({ "success", "status", "message", "path", "timestamp", "errors" })
@Schema(description = "Error response structure")
public class ApiError {

    @Schema(description = "Always false for errors")
    private final boolean success = false;

    @Schema(description = "HTTP status code")
    private final int status;

    @Schema(description = "Error message")
    private final String message;

    @Schema(description = "Request path")
    private final String path;

    @Schema(description = "Error timestamp")
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Schema(description = "List of field validation errors (null if not applicable)")
    private final List<ApiFieldError> errors;

    public ApiError(int status, String message, String path, List<ApiFieldError> errors) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

    public ApiError(int status, String message, String path) {
        this(status, message, path, null);
    }
}
