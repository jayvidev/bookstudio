package com.bookstudio.shared.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({ "field", "message", "rejectedValue" })
@Schema(description = "Field validation error details")
public class ApiFieldError {
    @Schema(description = "Field name that failed validation")
    private String field;

    @Schema(description = "Validation error message")
    private String message;

    @Schema(description = "The rejected value", nullable = true)
    private Object rejectedValue;
}
