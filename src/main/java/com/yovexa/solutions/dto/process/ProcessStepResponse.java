package com.yovexa.solutions.dto.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessStepResponse {
    private String id;
    private String stepNumber;
    private String phase;
    private String title;
    private String description;
    private List<String> details;
    private String icon;
    private String tag;
    private int displayOrder;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}

