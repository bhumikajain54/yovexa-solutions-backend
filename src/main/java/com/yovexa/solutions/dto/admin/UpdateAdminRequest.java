package com.yovexa.solutions.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdminRequest {

    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Updated administrator full name", example = "Jane Admin")
    private String name;

    @Email(message = "Please provide a valid email address")
    @Schema(description = "Updated administrator email address", example = "jane.updated@example.com")
    private String email;

    @Schema(description = "Account active status", example = "true")
    private Boolean isActive;
}
