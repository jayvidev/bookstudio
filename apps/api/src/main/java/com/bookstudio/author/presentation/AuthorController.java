package com.bookstudio.author.presentation;

import com.bookstudio.author.application.AuthorService;
import com.bookstudio.author.application.dto.request.CreateAuthorRequest;
import com.bookstudio.author.application.dto.request.UpdateAuthorRequest;
import com.bookstudio.author.application.dto.response.AuthorDetailResponse;
import com.bookstudio.author.application.dto.response.AuthorFilterOptionsResponse;
import com.bookstudio.author.application.dto.response.AuthorListResponse;
import com.bookstudio.author.application.dto.response.AuthorSelectOptionsResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Validated
@Tag(name = "Authors", description = "Operations related to authors")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    @Operation(summary = "List all authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authors listed successfully"),
            @ApiResponse(responseCode = "204", description = "No authors found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/authors\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<List<AuthorListResponse>>> list() {
        List<AuthorListResponse> authors = authorService.getList();
        ApiSuccess<List<AuthorListResponse>> response = new ApiSuccess<>(
                authors.isEmpty() ? "No authors found" : "Authors listed successfully",
                authors);

        HttpStatus status = authors.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/filter-options")
    @Operation(summary = "Get filter options for authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filter options retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No filter options found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/authors/filter-options\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<AuthorFilterOptionsResponse>> filterOptions() {
        AuthorFilterOptionsResponse options = authorService.getFilterOptions();

        boolean hasOptions = !options.nationalities().isEmpty();

        ApiSuccess<AuthorFilterOptionsResponse> response = new ApiSuccess<>(
                hasOptions ? "Filter options retrieved successfully" : "No filter options found",
                options);

        HttpStatus status = hasOptions ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/select-options")
    @Operation(summary = "Get select options for authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Select options retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No select options found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/authors/select-options\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<AuthorSelectOptionsResponse>> selectOptions() {
        AuthorSelectOptionsResponse options = authorService.getSelectOptions();

        boolean hasOptions = !options.nationalities().isEmpty();

        ApiSuccess<AuthorSelectOptionsResponse> response = new ApiSuccess<>(
                hasOptions ? "Select options retrieved successfully" : "No select options found",
                options);

        HttpStatus status = hasOptions ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an author by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Invalid ID", summary = "Invalid ID format", value = "{\"success\":false,\"status\":400,\"message\":\"Parameter 'id' has invalid value 'abc'\",\"path\":\"/authors/abc\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Not Found", summary = "Author not found", value = "{\"success\":false,\"status\":404,\"message\":\"Author not found with ID: 999\",\"path\":\"/authors/999\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/authors/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<AuthorDetailResponse>> get(@PathVariable @Min(value = 1, message = ValidationMessages.ID_MIN_VALUE) Long id) {
        AuthorDetailResponse author = authorService.getDetailById(id);
        return ResponseEntity.ok(new ApiSuccess<>("Author found", author));
    }

    @PostMapping
    @Operation(summary = "Create a new author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed or malformed JSON", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Validation Error", summary = "Validation failed", value = "{\"success\":false,\"status\":400,\"message\":\"Validation failed\",\"path\":\"/admin/authors\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":[{\"field\":\"name\",\"message\":\"must not be blank\",\"rejectedValue\":null}]}"))),
            @ApiResponse(responseCode = "409", description = "Database constraint violation", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Conflict Error", summary = "Database constraint violation", value = "{\"success\":false,\"status\":409,\"message\":\"Database error: constraint violation\",\"path\":\"/admin/authors\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/admin/authors\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<AuthorListResponse>> create(
            @Valid @RequestBody CreateAuthorRequest request) {
        AuthorListResponse created = authorService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiSuccess<>("Author created successfully", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an author by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed, malformed JSON, or invalid ID format", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Validation Error", summary = "Validation failed", value = "{\"success\":false,\"status\":400,\"message\":\"Validation failed\",\"path\":\"/admin/authors/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":[{\"field\":\"name\",\"message\":\"must not be blank\",\"rejectedValue\":null}]}"))),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Not Found", summary = "Author not found", value = "{\"success\":false,\"status\":404,\"message\":\"Author not found\",\"path\":\"/admin/authors/999\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "409", description = "Database constraint violation", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Conflict Error", summary = "Database constraint violation", value = "{\"success\":false,\"status\":409,\"message\":\"Database error: constraint violation\",\"path\":\"/admin/authors/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/admin/products/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<AuthorListResponse>> update(
            @PathVariable @Min(value = 1, message = ValidationMessages.ID_MIN_VALUE) Long id,
            @Valid @RequestBody UpdateAuthorRequest request) {
        AuthorListResponse updated = authorService.update(id, request);
        return ResponseEntity.ok(new ApiSuccess<>("Author updated successfully", updated));
    }
}
