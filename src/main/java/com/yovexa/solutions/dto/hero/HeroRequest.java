package com.yovexa.solutions.dto.hero;

import com.fasterxml.jackson.annotation.JsonAlias;
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
    private String eyebrow;

    @NotBlank(message = "Headline is required")
    @JsonAlias("heading")
    private String headline;

    @JsonAlias("highlightedText")
    private String highlightedHeadline;

    @NotBlank(message = "Description is required")
    private String description;

    private String heroImage;
    private String heroImageAlt;

    @JsonAlias("primaryCtaText")
    private String primaryCtaLabel;

    private String primaryCtaLink;

    @JsonAlias("secondaryCtaText")
    private String secondaryCtaLabel;

    private String secondaryCtaLink;

    @Builder.Default
    private String status = "DRAFT"; // DRAFT, PUBLISHED

    private Boolean isActive;
}
