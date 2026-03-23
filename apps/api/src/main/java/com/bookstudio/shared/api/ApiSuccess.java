package com.bookstudio.shared.api;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonPropertyOrder({ "success", "message", "data", "timestamp" })
@Schema(description = "Success response structure")
public class ApiSuccess<T> {

    @Schema(description = "Always true for successful responses")
    private final boolean success = true;

    @Schema(description = "Success message")
    private final String message;

    @Schema(description = "Response data")
    private final T data;

    @Schema(description = "Response timestamp")
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ApiSuccess(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
