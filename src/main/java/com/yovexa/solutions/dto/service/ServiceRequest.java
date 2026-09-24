package com.yovexa.solutions.dto.service;

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
public class ServiceRequest {

    @NotBlank(message = "Service title is required")
    @Schema(description = "Service title", example = "Custom Web Development")
    private String title;

    @Schema(description = "URL slug for service (auto-generated if omitted)", example = "custom-web-development")
    private String slug;

    @NotBlank(message = "Short description is required")
    @Schema(description = "Brief service summary", example = "End-to-end responsive web applications built with modern frameworks.")
    private String shortDescription;

    @Schema(description = "Comprehensive service details", example = "We build scalable, secure, and performant web solutions tailored to business needs.")
    private String description;

    @Schema(description = "Lucide icon identifier name", example = "Globe")
    private String icon;

    @Schema(description = "Popular badge tag text", example = "Most Popular")
    private String popularTag;

    @Schema(description = "List of key features included", example = "[\"Custom Frontend\", \"Robust Backend APIs\", \"Cloud Deployment\"]")
    private List<String> features;

    @Schema(description = "Call to action button text", example = "Get a Quote")
    private String buttonText;

    @Schema(description = "Call to action destination link", example = "/contact")
    private String buttonLink;

    @Builder.Default
    @Schema(description = "Display sort order", example = "1")
    private int displayOrder = 0;

    @Builder.Default
    @Schema(description = "Active status flag", example = "true")
    private Boolean isActive = true;
}

