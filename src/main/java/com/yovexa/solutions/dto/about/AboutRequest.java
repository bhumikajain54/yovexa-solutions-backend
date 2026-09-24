package com.yovexa.solutions.dto.about;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Section eyebrow badge", example = "About Yovexa Solutions")
    private String eyebrow;

    @NotBlank(message = "Title is required")
    @Schema(description = "About section headline", example = "We Build Software That Drives Business Growth")
    private String title;

    @JsonAlias({"titleHighlight", "highlightedText"})
    @Schema(description = "Highlighted keyword phrase in title", example = "Drives Business Growth")
    private String highlightedTitle;

    @NotBlank(message = "Primary paragraph is required")
    @JsonAlias({"description", "primaryParagraph"})
    @Schema(description = "Primary introduction paragraph", example = "Yovexa Solutions is a digital engineering agency delivering scalable enterprise software.")
    private String primaryParagraph;

    @JsonAlias({"additionalDescription", "secondaryParagraph"})
    @Schema(description = "Secondary detail paragraph", example = "From startups to enterprises, we architect robust backends, sleek frontends, and reliable cloud deployments.")
    private String secondaryParagraph;

    @JsonAlias({"primaryCtaText", "primaryButtonLabel"})
    @Schema(description = "Primary button label", example = "Our Services")
    private String primaryButtonLabel;

    @JsonAlias({"primaryCtaLink", "primaryButtonLink"})
    @Schema(description = "Primary button destination link", example = "/services")
    private String primaryButtonLink;

    @JsonAlias({"secondaryCtaText", "secondaryButtonLabel"})
    @Schema(description = "Secondary button label", example = "Get in Touch")
    private String secondaryButtonLabel;

    @JsonAlias({"secondaryCtaLink", "secondaryButtonLink"})
    @Schema(description = "Secondary button destination link", example = "/contact")
    private String secondaryButtonLink;

    @Schema(description = "About section illustration/photo URL", example = "https://images.unsplash.com/photo-1522071820081-009f0129c71c")
    private String image;

    @Schema(description = "Image alt text", example = "Yovexa Solutions engineering team collaboration")
    private String imageAlt;

    @Schema(description = "Key bullet points or value highlights", example = "[\"Scalable Cloud Architecture\", \"Agile Delivery\", \"24/7 Enterprise Support\"]")
    private List<String> highlights;

    @Builder.Default
    @Schema(description = "Publication status (DRAFT, PUBLISHED)", example = "PUBLISHED")
    private String status = "DRAFT";

    @Schema(description = "Active status flag (only one about section can be active at a time)", example = "true")
    private Boolean isActive;
}

