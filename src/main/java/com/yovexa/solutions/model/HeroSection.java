package com.yovexa.solutions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "hero_sections")
public class HeroSection {

    @Id
    private String id;

    private String eyebrow; // Also maps to badge
    private String headline; // Also maps to heading
    private String highlightedHeadline; // Also maps to highlightedText
    private String description;

    private String heroImage;
    private String heroImageAlt;

    private String primaryCtaLabel; // Also maps to primaryCtaText
    private String primaryCtaLink;

    private String secondaryCtaLabel; // Also maps to secondaryCtaText
    private String secondaryCtaLink;

    @Builder.Default
    @Indexed
    private String status = "DRAFT"; // DRAFT, PUBLISHED

    @Builder.Default
    @Indexed
    private boolean isActive = false;

    @CreatedDate
    @Indexed
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
