package com.yovexa.solutions.dto.process;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Step numeral or identifier", example = "01")
    private String stepNumber;

    @Schema(description = "Phase name", example = "Discovery & Strategy")
    private String phase;

    @NotBlank(message = "Step title is required")
    @Schema(description = "Process step title", example = "Requirements & Architectural Analysis")
    private String title;

    @NotBlank(message = "Description is required")
    @Schema(description = "Step overview description", example = "We analyze your business objectives, design the domain models, and prepare the project roadmap.")
    private String description;

    @Schema(description = "Detailed deliverables or checklist items", example = "[\"Technical Specification\", \"UI Wireframes\", \"Sprint Roadmap\"]")
    private List<String> details;

    @Schema(description = "Lucide icon identifier", example = "Search")
    private String icon;

    @Schema(description = "Badge or phase tag", example = "Phase 1")
    private String tag;

    @Builder.Default
    @Schema(description = "Display sort order", example = "1")
    private int displayOrder = 0;

    @Builder.Default
    @Schema(description = "Active status flag", example = "true")
    private Boolean isActive = true;
}

