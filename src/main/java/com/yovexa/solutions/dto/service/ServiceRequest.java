package com.yovexa.solutions.dto.service;

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
public class ServiceRequest {

    @NotBlank(message = "Service title is required")
    private String title;

    private String slug;

    @NotBlank(message = "Short description is required")
    private String shortDescription;

    private String description;
    private String icon;
    private String popularTag;
    private List<String> features;
    private String buttonText;
    private String buttonLink;

    @Builder.Default
    private int displayOrder = 0;

    @Builder.Default
    private Boolean isActive = true;
}

