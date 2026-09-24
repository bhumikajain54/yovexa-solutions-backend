package com.yovexa.solutions.dto.inquiry;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryStatusUpdateRequest {

    @NotBlank(message = "Status is required")
    @Schema(description = "Updated inquiry status", allowableValues = {"NEW", "CONTACTED", "IN_PROGRESS", "CLOSED"}, example = "IN_PROGRESS")
    private String status; // NEW, CONTACTED, IN_PROGRESS, CLOSED
}
