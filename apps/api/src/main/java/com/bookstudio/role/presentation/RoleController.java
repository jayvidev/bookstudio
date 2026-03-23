package com.bookstudio.role.presentation;

import com.bookstudio.role.application.RoleService;
import com.bookstudio.role.application.dto.request.CreateRoleRequest;
import com.bookstudio.role.application.dto.request.UpdateRoleRequest;
import com.bookstudio.role.application.dto.response.RoleDetailResponse;
import com.bookstudio.role.application.dto.response.RoleListResponse;
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
@RequestMapping("/roles")
@RequiredArgsConstructor
@Validated
@Tag(name = "Roles", description = "Operations related to roles")
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @Operation(summary = "List all roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles listed successfully (or empty list if no roles found)"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/roles\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<List<RoleListResponse>>> list() {
        List<RoleListResponse> roles = roleService.getList();
        ApiSuccess<List<RoleListResponse>> response = new ApiSuccess<>(
                roles.isEmpty() ? "No roles found" : "Roles listed successfully",
                roles);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a role by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Invalid ID", summary = "Invalid ID format", value = "{\"success\":false,\"status\":400,\"message\":\"Parameter 'id' has invalid value 'abc'\",\"path\":\"/roles/abc\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Not Found", summary = "Role not found", value = "{\"success\":false,\"status\":404,\"message\":\"Role not found with ID: 999\",\"path\":\"/roles/999\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/roles/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<RoleDetailResponse>> get(
            @Min(value = 1, message = ValidationMessages.ID_MIN_VALUE) @PathVariable Long id) {
        RoleDetailResponse role = roleService.getDetailById(id);
        return ResponseEntity.ok(new ApiSuccess<>("Role found", role));
    }

    @PostMapping
    @Operation(summary = "Create a new role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed or malformed JSON", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Validation Error", summary = "Validation failed", value = "{\"success\":false,\"status\":400,\"message\":\"Validation failed\",\"path\":\"/roles\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":[{\"field\":\"name\",\"message\":\"must not be blank\",\"rejectedValue\":null}]}"))),
            @ApiResponse(responseCode = "409", description = "Database constraint violation", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Conflict Error", summary = "Database constraint violation", value = "{\"success\":false,\"status\":409,\"message\":\"Database error: constraint violation\",\"path\":\"/roles\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/roles\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<RoleListResponse>> create(@Valid @RequestBody CreateRoleRequest request) {
        RoleListResponse created = roleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiSuccess<>("Role created successfully", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a role by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed, malformed JSON, or invalid ID format", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Validation Error", summary = "Validation failed", value = "{\"success\":false,\"status\":400,\"message\":\"Validation failed\",\"path\":\"/roles/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":[{\"field\":\"name\",\"message\":\"must not be blank\",\"rejectedValue\":null}]}"))),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Not Found", summary = "Role not found", value = "{\"success\":false,\"status\":404,\"message\":\"Role not found\",\"path\":\"/roles/999\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "409", description = "Database constraint violation", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Conflict Error", summary = "Database constraint violation", value = "{\"success\":false,\"status\":409,\"message\":\"Database error: constraint violation\",\"path\":\"/roles/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ApiError.class), examples = @ExampleObject(name = "Internal Error", summary = "Internal server error", value = "{\"success\":false,\"status\":500,\"message\":\"Internal server error\",\"path\":\"/roles/1\",\"timestamp\":\"2025-10-16T21:09:26.122Z\",\"errors\":null}")))
    })
    public ResponseEntity<ApiSuccess<RoleListResponse>> update(
            @Min(value = 1, message = ValidationMessages.ID_MIN_VALUE) @PathVariable Long id,
            @Valid @RequestBody UpdateRoleRequest request) {
        RoleListResponse updated = roleService.update(id, request);
        return ResponseEntity.ok(new ApiSuccess<>("Role updated successfully", updated));
    }
}
