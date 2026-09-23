package com.yovexa.solutions.dto.inquiry;

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
    private String status; // NEW, CONTACTED, IN_PROGRESS, CLOSED
}
