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
@Document(collection = "blogs")
public class Blog {

    @Id
    private String id;

    private String title;

    @Indexed(unique = true)
    private String slug;

    private String excerpt;
    private String content;

    private String featuredImage;

    @Indexed
    private String category;

    private String author;

    @Builder.Default
    private List<String> tags = new ArrayList<>();

    @Builder.Default
    @Indexed
    private String status = "DRAFT"; // DRAFT, PUBLISHED

    @Indexed
    private Instant publishedAt;

    private String seoTitle;
    private String seoDescription;

    @CreatedDate
    @Indexed
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
