package com.yovexa.solutions.dto.project;

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
public class ProjectResponse {
    private String id;
    private String name;
    private String title;
    private String projectName;
    private String slug;
    private String shortDescription;
    private String summary;
    private String description;
    private String fullDescription;
    private String solution;
    private String category;
    private String projectType;
    private String featuredImage;
    private List<String> galleryImages;
    private List<String> technologies;
    private String projectUrl;
    private String githubUrl;
    private String caseStudyUrl;
    private String status;
    private boolean featured;
    private int displayOrder;
    private String seoTitle;
    private String seoDescription;
    private Instant createdAt;
    private Instant updatedAt;
}
