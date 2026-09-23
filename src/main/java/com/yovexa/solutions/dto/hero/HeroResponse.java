package com.yovexa.solutions.dto.hero;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeroResponse {
    private String id;
    private String eyebrow;
    private String badge;
    private String headline;
    private String heading;
    private String highlightedHeadline;
    private String highlightedText;
    private String description;
    private String heroImage;
    private String heroImageAlt;
    private String primaryCtaLabel;
    private String primaryCtaText;
    private String primaryCtaLink;
    private String secondaryCtaLabel;
    private String secondaryCtaText;
    private String secondaryCtaLink;
    private String status;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}
