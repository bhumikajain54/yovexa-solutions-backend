package com.yovexa.solutions.dto.about;

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
public class AboutRequest {

    @JsonAlias({"badge", "sectionLabel"})
    private String eyebrow;

    @NotBlank(message = "Title is required")
    private String title;

    @JsonAlias({"titleHighlight", "highlightedText"})
    private String highlightedTitle;

    @NotBlank(message = "Primary paragraph is required")
    @JsonAlias({"description", "primaryParagraph"})
    private String primaryParagraph;

    @JsonAlias({"additionalDescription", "secondaryParagraph"})
    private String secondaryParagraph;

    @JsonAlias({"primaryCtaText", "primaryButtonLabel"})
    private String primaryButtonLabel;

    @JsonAlias({"primaryCtaLink", "primaryButtonLink"})
    private String primaryButtonLink;

    @JsonAlias({"secondaryCtaText", "secondaryButtonLabel"})
    private String secondaryButtonLabel;

    @JsonAlias({"secondaryCtaLink", "secondaryButtonLink"})
    private String secondaryButtonLink;

    private String image;
    private String imageAlt;

    private List<String> highlights;

    @Builder.Default
    private String status = "DRAFT";

    private Boolean isActive;
}

