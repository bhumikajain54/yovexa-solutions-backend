package com.yovexa.solutions.controller;

import com.yovexa.solutions.dto.admin.AdminResponse;
import com.yovexa.solutions.dto.auth.LoginRequest;
import com.yovexa.solutions.dto.auth.LoginResponse;
import com.yovexa.solutions.dto.auth.RegisterRequest;
import com.yovexa.solutions.dto.common.ApiResponse;
import com.yovexa.solutions.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Admin Registration, Login, and Session Endpoints")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(
        summary = "Register initial admin",
        description = "Creates the initial administrator account. Allowed only when zero admins exist in the database; returns 409 Conflict once an administrator is already registered."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Admin account registered successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed - invalid input parameters"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Registration conflict - an administrator account already exists")
    })
    public ResponseEntity<ApiResponse<AdminResponse>> register(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Admin registration details (name, email, password, confirmPassword)", required = true)
            RegisterRequest request) {
        AdminResponse response = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Admin account created successfully.", response));
    }

    @PostMapping("/login")
    @Operation(
        summary = "Admin login",
        description = "Authenticates administrator email and password, returning a JWT Bearer token upon success."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Authentication successful - JWT token issued"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed - invalid email or password format"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials or deactivated account")
    })
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Admin login credentials (email and password)", required = true)
            LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful.", response));
    }

    @PostMapping("/logout")
    @Operation(
        summary = "Admin logout",
        description = "Logs out current administrator session."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Logout successful")
    })
    public ResponseEntity<ApiResponse<Void>> logout() {
        authService.logout();
        return ResponseEntity.ok(ApiResponse.successMessage("Logout successful"));
    }
}

