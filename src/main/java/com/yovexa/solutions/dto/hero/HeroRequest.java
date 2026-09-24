package com.yovexa.solutions.dto.hero;

import com.fasterxml.jackson.annotation.JsonAlias;
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
public class HeroRequest {

    @JsonAlias("badge")
    @Schema(description = "Eyebrow badge text above the headline", example = "Transform Your Digital Presence")
    private String eyebrow;

    @NotBlank(message = "Headline is required")
    @JsonAlias("heading")
    @Schema(description = "Primary hero headline", example = "Innovating Tomorrow's Solutions Today")
    private String headline;

    @JsonAlias("highlightedText")
    @Schema(description = "Highlighted keyword phrase within headline", example = "Tomorrow's Solutions")
    private String highlightedHeadline;

    @NotBlank(message = "Description is required")
    @Schema(description = "Subheading / paragraph description", example = "Empowering businesses through cutting-edge web, mobile, and cloud development.")
    private String description;

    @Schema(description = "Hero banner illustration/image URL", example = "https://images.unsplash.com/photo-1451187580459-43490279c0fa")
    private String heroImage;

    @Schema(description = "Hero image alt text", example = "Yovexa Solutions Technology Innovation")
    private String heroImageAlt;

    @JsonAlias("primaryCtaText")
    @Schema(description = "Primary call to action button text", example = "Explore Projects")
    private String primaryCtaLabel;

    @Schema(description = "Primary call to action link target", example = "/projects")
    private String primaryCtaLink;

    @JsonAlias("secondaryCtaText")
    @Schema(description = "Secondary call to action button text", example = "Contact Us")
    private String secondaryCtaLabel;

    @Schema(description = "Secondary call to action link target", example = "/contact")
    private String secondaryCtaLink;

    @Builder.Default
    @Schema(description = "Publication status (DRAFT, PUBLISHED)", example = "PUBLISHED")
    private String status = "DRAFT"; // DRAFT, PUBLISHED

    @Schema(description = "Active status flag (only one hero can be active at a time)", example = "true")
    private Boolean isActive;
}
