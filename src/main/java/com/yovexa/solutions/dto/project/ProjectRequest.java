package com.yovexa.solutions.dto.project;

import com.fasterxml.jackson.annotation.JsonAlias;
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
public class ProjectRequest {

    @NotBlank(message = "Project name is required")
    @JsonAlias({"title", "projectName"})
    private String name;

    private String slug;

    @NotBlank(message = "Short description is required")
    @JsonAlias("summary")
    private String shortDescription;

    @JsonAlias({"solution", "fullDescription"})
    private String description;

    @Builder.Default
    private String category = "WEB_APPLICATIONS"; // WEB_APPLICATIONS, MOBILE_APPS, BUSINESS_SYSTEMS, E_COMMERCE, SAAS_PLATFORMS

    private String projectType;
    private String featuredImage;
    private List<String> galleryImages;
    private List<String> technologies;

    private String projectUrl;
    private String githubUrl;
    private String caseStudyUrl;

    @Builder.Default
    private String status = "DRAFT"; // DRAFT, PUBLISHED

    @Builder.Default
    private Boolean featured = false;

    @Builder.Default
    private int displayOrder = 0;

    private String seoTitle;
    private String seoDescription;
}
