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
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "projects")
public class Project {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String slug;

    private String shortDescription;
    private String description;

    @Indexed
    private String category; // WEB_APPLICATIONS, MOBILE_APPS, BUSINESS_SYSTEMS, E_COMMERCE, SAAS_PLATFORMS

    private String projectType;

    private String featuredImage;

    @Builder.Default
    private List<String> galleryImages = new ArrayList<>();

    @Builder.Default
    private List<String> technologies = new ArrayList<>();

    private String projectUrl;
    private String githubUrl;
    private String caseStudyUrl;

    @Builder.Default
    @Indexed
    private String status = "DRAFT"; // DRAFT, PUBLISHED

    @Builder.Default
    @Indexed
    private boolean featured = false;

    @Builder.Default
    @Indexed
    private int displayOrder = 0;

    private String seoTitle;
    private String seoDescription;

    @CreatedDate
    @Indexed
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
