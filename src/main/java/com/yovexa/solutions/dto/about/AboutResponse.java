package com.yovexa.solutions.dto.about;

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
public class AboutResponse {
    private String id;
    private String eyebrow;
    private String badge;
    private String sectionLabel;
    private String title;
    private String highlightedTitle;
    private String titleHighlight;
    private String primaryParagraph;
    private String description;
    private String secondaryParagraph;
    private String additionalDescription;
    private String primaryButtonLabel;
    private String primaryCtaText;
    private String primaryButtonLink;
    private String primaryCtaLink;
    private String secondaryButtonLabel;
    private String secondaryCtaText;
    private String secondaryButtonLink;
    private String secondaryCtaLink;
    private String image;
    private String imageAlt;
    private String imageCategory;
    private String imageTitle;
    private String imageBadge;
    private List<String> highlights;
    private String status;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}

