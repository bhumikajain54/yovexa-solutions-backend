package com.yovexa.solutions.dto.blog;

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
public class BlogResponse {
    private String id;
    private String title;
    private String slug;
    private String excerpt;
    private String content;
    private String featuredImage;
    private String category;
    private String author;
    private List<String> tags;
    private String status;
    private Instant publishedAt;
    private String readTime;
    private String seoTitle;
    private String seoDescription;
    private Instant createdAt;
    private Instant updatedAt;
}
