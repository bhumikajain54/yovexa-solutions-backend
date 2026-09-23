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
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "about_sections")
public class AboutSection {

    @Id
    private String id;

    private String eyebrow;
    private String title;
    private String highlightedTitle;
    private String primaryParagraph;
    private String secondaryParagraph;

    private String primaryButtonLabel;
    private String primaryButtonLink;

    private String secondaryButtonLabel;
    private String secondaryButtonLink;

    private String image;
    private String imageAlt;

    private List<String> highlights;

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
