package com.bookstudio.fine.presentation;

import com.bookstudio.fine.application.FineService;
import com.bookstudio.fine.application.dto.request.CreateFineRequest;
import com.bookstudio.fine.application.dto.request.UpdateFineRequest;
import com.bookstudio.fine.application.dto.response.FineDetailResponse;
import com.bookstudio.fine.application.dto.response.FineFilterOptionsResponse;
import com.bookstudio.fine.application.dto.response.FineListResponse;
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
@RequestMapping("/fines")
@RequiredArgsConstructor
@Validated
@Tag(name = "Fines", description = "Operations related to fines")
public class FineController {
    private final FineService fineService;

    @GetMapping
    @Operation(summary = "List all fines")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fines listed successfully (or empty list if no fines found)"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/fines\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<List<FineListResponse>>> list() {
        List<FineListResponse> fines = fineService.getList();
        ApiSuccess<List<FineListResponse>> response = new ApiSuccess<>(
                fines.isEmpty() ? "No fines found" : "Fines listed successfully",
                fines);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter-options")
    @Operation(summary = "Get filter options for fines")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filter options retrieved successfully (or empty if no options found)"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/fines/filter-options\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<FineFilterOptionsResponse>> filterOptions() {
        FineFilterOptionsResponse options = fineService.getFilterOptions();

        boolean hasOptions = !options.loans().isEmpty() || !options.copies().isEmpty();

        ApiSuccess<FineFilterOptionsResponse> response = new ApiSuccess<>(
                hasOptions ? "Filter options retrieved successfully" : "No filter options found",
                options);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a fine by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fine found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Invalid ID", summary = "Invalid ID format", value = "{\"success\":false,\"status\":400,\"message\":\"Parameter 'id' has invalid value 'abc'\",\"path\":\"/fines/abc\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "404", description = "Fine not found", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Not Found", summary = "Fine not found", value = "{\"success\":false,\"status\":404,\"message\":\"Fine not found with ID: 999\",\"path\":\"/fines/999\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/fines/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<FineDetailResponse>> get(
            @PathVariable @Min(value = 1, message = ValidationMessages.ID_MIN_VALUE) Long id) {
        FineDetailResponse fine = fineService.getDetailById(id);
        return ResponseEntity.ok(new ApiSuccess<>("Fine found", fine));
    }

    @PostMapping
    @Operation(summary = "Create a new fine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fine created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed or malformed JSON", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Validation Error", summary = "Validation failed", value = "{\"success\":false,\"status\":400,\"message\":\"Validation failed\",\"path\":\"/fines\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":[{\"field\":\"amount\",\"message\":\"must not be null\",\"rejectedValue\":null}]}"))),
            @ApiResponse(responseCode = "409", description = "Database constraint violation", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Conflict Error", summary = "Database constraint violation", value = "{\"success\":false,\"status\":409,\"message\":\"Database error: constraint violation\",\"path\":\"/fines\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/fines\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<FineListResponse>> create(@Valid @RequestBody CreateFineRequest request) {
        FineListResponse created = fineService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiSuccess<>("Fine created successfully", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a fine by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fine updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed, malformed JSON, or invalid ID format", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Validation Error", summary = "Validation failed", value = "{\"success\":false,\"status\":400,\"message\":\"Validation failed\",\"path\":\"/fines/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":[{\"field\":\"amount\",\"message\":\"must not be null\",\"rejectedValue\":null}]}"))),
            @ApiResponse(responseCode = "404", description = "Fine not found", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Not Found", summary = "Fine not found", value = "{\"success\":false,\"status\":404,\"message\":\"Fine not found\",\"path\":\"/fines/999\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "409", description = "Database constraint violation", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Conflict Error", summary = "Database constraint violation", value = "{\"success\":false,\"status\":409,\"message\":\"Database error: constraint violation\",\"path\":\"/fines/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/fines/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<FineListResponse>> update(
            @PathVariable @Min(value = 1, message = ValidationMessages.ID_MIN_VALUE) Long id,
            @Valid @RequestBody UpdateFineRequest request) {
        FineListResponse updated = fineService.update(id, request);
        return ResponseEntity.ok(new ApiSuccess<>("Fine updated successfully", updated));
    }
}
