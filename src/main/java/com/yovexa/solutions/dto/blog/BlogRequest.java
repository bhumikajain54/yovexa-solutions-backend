package com.yovexa.solutions.dto.blog;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class BlogRequest {

    @NotBlank(message = "Blog title is required")
    @Schema(description = "Blog article title", example = "Building Scalable Enterprise Architectures with Spring Boot 3")
    private String title;

    @Schema(description = "URL slug for blog article (auto-generated if omitted)", example = "building-scalable-enterprise-architectures-with-spring-boot-3")
    private String slug;

    @NotBlank(message = "Blog excerpt is required")
    @Schema(description = "Short preview summary for blog cards", example = "Insights into high-performance Spring Boot architectures and cloud microservices.")
    private String excerpt;

    @NotBlank(message = "Blog content is required")
    @Schema(description = "Full article content in Markdown or HTML format", example = "# Scalable Architectures\nSpring Boot 3 brings AOT compilation and native images...")
    private String content;

    @Schema(description = "Cover image URL", example = "https://images.unsplash.com/photo-1518770660439-4636190af475")
    private String featuredImage;

    @NotBlank(message = "Category is required")
    @Schema(description = "Article category", example = "Engineering")
    private String category;

    @NotBlank(message = "Author is required")
    @Schema(description = "Author name", example = "Yovexa Tech Team")
    private String author;

    @Schema(description = "Keywords or tags for categorization", example = "[\"Spring Boot\", \"Java\", \"Architecture\"]")
    private List<String> tags;

    @Builder.Default
    @Schema(description = "Publication status (DRAFT, PUBLISHED)", example = "PUBLISHED")
    private String status = "DRAFT"; // DRAFT, PUBLISHED

    @Schema(description = "Publication timestamp")
    private Instant publishedAt;

    @Schema(description = "SEO meta title", example = "Building Scalable Architectures | Yovexa Solutions")
    private String seoTitle;

    @Schema(description = "SEO meta description", example = "Technical guide on building modern enterprise applications.")
    private String seoDescription;
}
