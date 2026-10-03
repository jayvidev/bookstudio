package com.bookstudio.staff.auth.presentation;

import com.bookstudio.shared.api.ApiError;
import com.bookstudio.shared.api.ApiSuccess;
import com.bookstudio.shared.security.CurrentUser;
import com.bookstudio.staff.auth.application.AuthService;
import com.bookstudio.staff.auth.application.dto.request.LoginRequest;
import com.bookstudio.staff.auth.application.dto.request.RefreshTokenRequest;
import com.bookstudio.staff.auth.application.dto.response.AuthResponse;
import com.bookstudio.staff.auth.application.dto.response.AuthUserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Auth", description = "Staff authentication")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @SecurityRequirements
    @Operation(summary = "Log in with username and password",
            description = "Returns a short-lived access token (Bearer) and a refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ApiSuccess<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(new ApiSuccess<>("Logged in", authService.login(request.username(), request.password())));
    }

    @PostMapping("/demo")
    @SecurityRequirements
    @Operation(summary = "One-click demo login", description = "Only available when the demo is enabled.")
    public ResponseEntity<ApiSuccess<AuthResponse>> demo() {
        return ResponseEntity.ok(new ApiSuccess<>("Logged in as demo user", authService.demoLogin()));
    }

    @PostMapping("/refresh")
    @SecurityRequirements
    @Operation(summary = "Exchange a refresh token for a new token pair",
            description = "The presented refresh token is revoked (rotation). Reusing a revoked token revokes all sessions of that user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New tokens issued"),
            @ApiResponse(responseCode = "401", description = "Invalid, expired or revoked refresh token", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ApiSuccess<AuthResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(new ApiSuccess<>("Tokens refreshed", authService.refresh(request.refreshToken())));
    }

    @PostMapping("/logout")
    @Operation(summary = "Revoke a refresh token")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request) {
        authService.logout(request.refreshToken());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Current user with role and permissions")
    public ResponseEntity<ApiSuccess<AuthUserResponse>> me(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(new ApiSuccess<>("Current user", authService.currentUser(CurrentUser.id(jwt))));
    }
}
