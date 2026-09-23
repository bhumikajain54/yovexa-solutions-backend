package com.yovexa.solutions.dto.service;

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
public class ServiceResponse {
    private String id;
    private String title;
    private String slug;
    private String shortDescription;
    private String description;
    private String icon;
    private String popularTag;
    private List<String> features;
    private String buttonText;
    private String buttonLink;
    private int displayOrder;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}

