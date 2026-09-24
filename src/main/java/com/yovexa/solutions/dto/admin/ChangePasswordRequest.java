package com.yovexa.solutions.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "Current password is required")
    @Schema(description = "Existing administrator password", example = "OldPassword123")
    private String currentPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "New password must be at least 8 characters long")
    @Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$",
        message = "New password must contain at least one letter and one number"
    )
    @Schema(description = "New password (at least 8 characters, 1 letter, 1 number)", example = "NewStrongPassword123")
    private String newPassword;

    @NotBlank(message = "Confirm password is required")
    @Schema(description = "Confirmation of new password", example = "NewStrongPassword123")
    private String confirmPassword;
}
