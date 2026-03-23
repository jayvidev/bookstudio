package com.bookstudio.reader.presentation;

import com.bookstudio.reader.application.ReaderService;
import com.bookstudio.reader.application.dto.request.CreateReaderRequest;
import com.bookstudio.reader.application.dto.request.UpdateReaderRequest;
import com.bookstudio.reader.application.dto.response.ReaderDetailResponse;
import com.bookstudio.reader.application.dto.response.ReaderListResponse;
import com.bookstudio.shared.api.ApiError;
import com.bookstudio.shared.api.ApiSuccess;
import com.bookstudio.shared.validation.ValidationMessages;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/readers")
@RequiredArgsConstructor
@Validated
@Tag(name = "Readers", description = "Operations related to readers")
public class ReaderController {
    private final ReaderService readerService;

    @GetMapping
    @Operation(summary = "List all readers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Readers listed successfully (or empty list if no readers found)"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/readers\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<List<ReaderListResponse>>> list() {
        List<ReaderListResponse> readers = readerService.getList();
        ApiSuccess<List<ReaderListResponse>> response = new ApiSuccess<>(
                readers.isEmpty() ? "No readers found" : "Readers listed successfully",
                readers);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a reader by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reader found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Invalid ID", summary = "Invalid ID format", value = "{\"success\":false,\"status\":400,\"message\":\"Parameter 'id' has invalid value 'abc'\",\"path\":\"/readers/abc\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "404", description = "Reader not found", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Not Found", summary = "Reader not found", value = "{\"success\":false,\"status\":404,\"message\":\"Reader not found with ID: 999\",\"path\":\"/readers/999\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/readers/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<ReaderDetailResponse>> get(
            @PathVariable @Min(value = 1, message = ValidationMessages.ID_MIN_VALUE) Long id) {
        ReaderDetailResponse reader = readerService.getDetailById(id);
        return ResponseEntity.ok(new ApiSuccess<>("Reader found", reader));
    }

    @PostMapping
    @Operation(summary = "Create a new reader")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reader created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed or malformed JSON", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Validation Error", summary = "Validation failed", value = "{\"success\":false,\"status\":400,\"message\":\"Validation failed\",\"path\":\"/readers\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":[{\"field\":\"name\",\"message\":\"must not be blank\",\"rejectedValue\":null}]}"))),
            @ApiResponse(responseCode = "409", description = "Database constraint violation", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Conflict Error", summary = "Database constraint violation", value = "{\"success\":false,\"status\":409,\"message\":\"Database error: constraint violation\",\"path\":\"/readers\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/readers\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<ReaderListResponse>> create(@Valid @RequestBody CreateReaderRequest request) {
        ReaderListResponse created = readerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiSuccess<>("Reader created successfully", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a reader by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reader updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed, malformed JSON, or invalid ID format", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Validation Error", summary = "Validation failed", value = "{\"success\":false,\"status\":400,\"message\":\"Validation failed\",\"path\":\"/readers/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":[{\"field\":\"name\",\"message\":\"must not be blank\",\"rejectedValue\":null}]}"))),
            @ApiResponse(responseCode = "404", description = "Reader not found", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Not Found", summary = "Reader not found", value = "{\"success\":false,\"status\":404,\"message\":\"Reader not found\",\"path\":\"/readers/999\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "409", description = "Database constraint violation", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Conflict Error", summary = "Database constraint violation", value = "{\"success\":false,\"status\":409,\"message\":\"Database error: constraint violation\",\"path\":\"/readers/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/readers/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<ReaderListResponse>> update(
            @PathVariable @Min(value = 1, message = ValidationMessages.ID_MIN_VALUE) Long id,
            @Valid @RequestBody UpdateReaderRequest request) {
        ReaderListResponse updated = readerService.update(id, request);
        return ResponseEntity.ok(new ApiSuccess<>("Reader updated successfully", updated));
    }
}
