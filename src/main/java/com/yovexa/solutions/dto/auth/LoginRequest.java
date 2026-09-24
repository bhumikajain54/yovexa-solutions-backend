package com.yovexa.solutions.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Administrator registered email address", example = "admin@example.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(description = "Administrator password", example = "StrongPassword123")
    private String password;
}
