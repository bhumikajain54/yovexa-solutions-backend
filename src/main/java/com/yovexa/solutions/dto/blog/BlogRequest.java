package com.yovexa.solutions.dto.blog;

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
    private String title;

    private String slug;

    @NotBlank(message = "Blog excerpt is required")
    private String excerpt;

    @NotBlank(message = "Blog content is required")
    private String content;

    private String featuredImage;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Author is required")
    private String author;

    private List<String> tags;

    @Builder.Default
    private String status = "DRAFT"; // DRAFT, PUBLISHED

    private Instant publishedAt;

    private String seoTitle;
    private String seoDescription;
}
