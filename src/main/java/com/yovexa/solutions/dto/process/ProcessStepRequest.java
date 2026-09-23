package com.yovexa.solutions.dto.process;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessStepRequest {

    private String stepNumber;
    private String phase;

    @NotBlank(message = "Step title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private List<String> details;
    private String icon;
    private String tag;

    @Builder.Default
    private int displayOrder = 0;

    @Builder.Default
    private Boolean isActive = true;
}

