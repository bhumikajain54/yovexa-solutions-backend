package com.yovexa.solutions.mapper;

import com.yovexa.solutions.dto.about.AboutRequest;
import com.yovexa.solutions.dto.about.AboutResponse;
import com.yovexa.solutions.dto.auth.UserSummaryDto;
import com.yovexa.solutions.dto.blog.BlogRequest;
import com.yovexa.solutions.dto.blog.BlogResponse;
import com.yovexa.solutions.dto.hero.HeroRequest;
import com.yovexa.solutions.dto.hero.HeroResponse;
import com.yovexa.solutions.dto.inquiry.ContactInquiryRequest;
import com.yovexa.solutions.dto.inquiry.ContactInquiryResponse;
import com.yovexa.solutions.dto.process.ProcessStepRequest;
import com.yovexa.solutions.dto.process.ProcessStepResponse;
import com.yovexa.solutions.dto.project.ProjectRequest;
import com.yovexa.solutions.dto.project.ProjectResponse;
import com.yovexa.solutions.dto.service.ServiceRequest;
import com.yovexa.solutions.dto.service.ServiceResponse;
import com.yovexa.solutions.dto.settings.SiteSettingsRequest;
import com.yovexa.solutions.dto.settings.SiteSettingsResponse;
import com.yovexa.solutions.model.*;
import com.yovexa.solutions.util.SlugUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;
import com.yovexa.solutions.dto.admin.AdminProfileResponse;
import com.yovexa.solutions.dto.admin.AdminResponse;
import java.util.ArrayList;

@Component
public class EntityMapper {

    // ADMIN
    public UserSummaryDto toUserSummaryDto(Admin admin) {
        if (admin == null)
            return null;
        return UserSummaryDto.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .role(admin.getRole())
                .build();
    }

    public AdminResponse toAdminResponse(Admin admin) {
        if (admin == null)
            return null;
        return AdminResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .role(admin.getRole())
                .isActive(admin.getIsActive() != null ? admin.getIsActive() : true)
                .createdAt(admin.getCreatedAt())
                .updatedAt(admin.getUpdatedAt())
                .build();
    }

    public AdminProfileResponse toAdminProfileResponse(Admin admin) {
        if (admin == null)
            return null;
        return AdminProfileResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .role(admin.getRole())
                .isActive(admin.getIsActive() != null ? admin.getIsActive() : true)
                .build();
    }

    // HERO
    public HeroResponse toHeroResponse(HeroSection hero) {
        if (hero == null)
            return null;
        return HeroResponse.builder()
                .id(hero.getId())
                .eyebrow(hero.getEyebrow())
                .badge(hero.getEyebrow())
                .headline(hero.getHeadline())
                .heading(hero.getHeadline())
                .highlightedHeadline(hero.getHighlightedHeadline())
                .highlightedText(hero.getHighlightedHeadline())
                .description(hero.getDescription())
                .heroImage(hero.getHeroImage())
                .heroImageAlt(hero.getHeroImageAlt())
                .primaryCtaLabel(hero.getPrimaryCtaLabel())
                .primaryCtaText(hero.getPrimaryCtaLabel())
                .primaryCtaLink(hero.getPrimaryCtaLink())
                .secondaryCtaLabel(hero.getSecondaryCtaLabel())
                .secondaryCtaText(hero.getSecondaryCtaLabel())
                .secondaryCtaLink(hero.getSecondaryCtaLink())
                .status(hero.getStatus())
                .isActive(hero.isActive())
                .createdAt(hero.getCreatedAt())
                .updatedAt(hero.getUpdatedAt())
                .build();
    }

    public HeroSection toHeroSection(HeroRequest request) {
        if (request == null)
            return null;
        return HeroSection.builder()
                .eyebrow(request.getEyebrow())
                .headline(request.getHeadline())
                .highlightedHeadline(request.getHighlightedHeadline())
                .description(request.getDescription())
                .heroImage(request.getHeroImage())
                .heroImageAlt(request.getHeroImageAlt())
                .primaryCtaLabel(request.getPrimaryCtaLabel())
                .primaryCtaLink(request.getPrimaryCtaLink())
                .secondaryCtaLabel(request.getSecondaryCtaLabel())
                .secondaryCtaLink(request.getSecondaryCtaLink())
                .status(request.getStatus() != null ? request.getStatus().toUpperCase() : "DRAFT")
                .isActive(request.getIsActive() != null && request.getIsActive())
                .build();
    }

    // ABOUT
    public AboutResponse toAboutResponse(AboutSection about) {
        if (about == null)
            return null;
        return AboutResponse.builder()
                .id(about.getId())
                .eyebrow(about.getEyebrow())
                .badge(about.getEyebrow())
                .sectionLabel(about.getEyebrow())
                .title(about.getTitle())
                .highlightedTitle(about.getHighlightedTitle())
                .titleHighlight(about.getHighlightedTitle())
                .primaryParagraph(about.getPrimaryParagraph())
                .description(about.getPrimaryParagraph())
                .secondaryParagraph(about.getSecondaryParagraph())
                .additionalDescription(about.getSecondaryParagraph())
                .primaryButtonLabel(about.getPrimaryButtonLabel())
                .primaryCtaText(about.getPrimaryButtonLabel())
                .primaryButtonLink(about.getPrimaryButtonLink())
                .primaryCtaLink(about.getPrimaryButtonLink())
                .secondaryButtonLabel(about.getSecondaryButtonLabel())
                .secondaryCtaText(about.getSecondaryButtonLabel())
                .secondaryButtonLink(about.getSecondaryButtonLink())
                .secondaryCtaLink(about.getSecondaryButtonLink())
                .image(about.getImage())
                .imageAlt(about.getImageAlt())
                .imageCategory(about.getImageCategory())
                .imageTitle(about.getImageTitle())
                .imageBadge(about.getImageBadge())
                .highlights(about.getHighlights() != null ? about.getHighlights() : new ArrayList<>())
                .status(about.getStatus())
                .isActive(about.isActive())
                .createdAt(about.getCreatedAt())
                .updatedAt(about.getUpdatedAt())
                .build();
    }

    public AboutSection toAboutSection(AboutRequest request) {
        if (request == null)
            return null;
        return AboutSection.builder()
                .eyebrow(request.getEyebrow())
                .title(request.getTitle())
                .highlightedTitle(request.getHighlightedTitle())
                .primaryParagraph(request.getPrimaryParagraph())
                .secondaryParagraph(request.getSecondaryParagraph())
                .primaryButtonLabel(request.getPrimaryButtonLabel())
                .primaryButtonLink(request.getPrimaryButtonLink())
                .secondaryButtonLabel(request.getSecondaryButtonLabel())
                .secondaryButtonLink(request.getSecondaryButtonLink())
                .image(request.getImage())
                .imageAlt(request.getImageAlt())
                .imageCategory(request.getImageCategory())
                .imageTitle(request.getImageTitle())
                .imageBadge(request.getImageBadge())
                .highlights(request.getHighlights() != null ? request.getHighlights() : new ArrayList<>())
                .status(request.getStatus() != null ? request.getStatus().toUpperCase() : "DRAFT")
                .isActive(request.getIsActive() != null && request.getIsActive())
                .build();
    }

    // SERVICE
    public ServiceResponse toServiceResponse(Service service) {
        if (service == null)
            return null;
        return ServiceResponse.builder()
                .id(service.getId())
                .title(service.getTitle())
                .slug(service.getSlug())
                .shortDescription(service.getShortDescription())
                .description(service.getDescription())
                .icon(service.getIcon())
                .popularTag(service.getPopularTag())
                .features(service.getFeatures() != null ? service.getFeatures() : new ArrayList<>())
                .buttonText(service.getButtonText())
                .buttonLink(service.getButtonLink())
                .displayOrder(service.getDisplayOrder())
                .isActive(service.isActive())
                .createdAt(service.getCreatedAt())
                .updatedAt(service.getUpdatedAt())
                .build();
    }

    public Service toService(ServiceRequest request) {
        if (request == null)
            return null;
        String slug = (request.getSlug() != null && !request.getSlug().trim().isEmpty())
                ? SlugUtils.toSlug(request.getSlug())
                : SlugUtils.toSlug(request.getTitle());

        return Service.builder()
                .title(request.getTitle())
                .slug(slug)
                .shortDescription(request.getShortDescription())
                .description(request.getDescription())
                .icon(request.getIcon())
                .popularTag(request.getPopularTag())
                .features(request.getFeatures() != null ? request.getFeatures() : new ArrayList<>())
                .buttonText(request.getButtonText())
                .buttonLink(request.getButtonLink())
                .displayOrder(request.getDisplayOrder())
                .isActive(request.getIsActive() == null || request.getIsActive())
                .build();
    }

    // PROCESS STEP
    public ProcessStepResponse toProcessStepResponse(ProcessStep step) {
        if (step == null)
            return null;
        return ProcessStepResponse.builder()
                .id(step.getId())
                .stepNumber(step.getStepNumber())
                .phase(step.getPhase())
                .title(step.getTitle())
                .description(step.getDescription())
                .details(step.getDetails() != null ? step.getDetails() : new ArrayList<>())
                .icon(step.getIcon())
                .tag(step.getTag())
                .displayOrder(step.getDisplayOrder())
                .isActive(step.isActive())
                .createdAt(step.getCreatedAt())
                .updatedAt(step.getUpdatedAt())
                .build();
    }

    public ProcessStep toProcessStep(ProcessStepRequest request) {
        if (request == null)
            return null;
        return ProcessStep.builder()
                .stepNumber(request.getStepNumber())
                .phase(request.getPhase())
                .title(request.getTitle())
                .description(request.getDescription())
                .details(request.getDetails() != null ? request.getDetails() : new ArrayList<>())
                .icon(request.getIcon())
                .tag(request.getTag())
                .displayOrder(request.getDisplayOrder())
                .isActive(request.getIsActive() == null || request.getIsActive())
                .build();
    }

    // PROJECT
    public ProjectResponse toProjectResponse(Project project) {
        if (project == null)
            return null;
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .title(project.getName())
                .projectName(project.getName())
                .slug(project.getSlug())
                .shortDescription(project.getShortDescription())
                .summary(project.getShortDescription())
                .description(project.getDescription())
                .fullDescription(project.getDescription())
                .solution(project.getDescription())
                .category(project.getCategory())
                .projectType(project.getProjectType())
                .featuredImage(project.getFeaturedImage())
                .galleryImages(project.getGalleryImages() != null ? project.getGalleryImages() : new ArrayList<>())
                .technologies(project.getTechnologies() != null ? project.getTechnologies() : new ArrayList<>())
                .projectUrl(project.getProjectUrl())
                .githubUrl(project.getGithubUrl())
                .caseStudyUrl(project.getCaseStudyUrl())
                .status(project.getStatus())
                .featured(project.isFeatured())
                .displayOrder(project.getDisplayOrder())
                .seoTitle(project.getSeoTitle())
                .seoDescription(project.getSeoDescription())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }

    public Project toProject(ProjectRequest request) {
        if (request == null)
            return null;
        String slug = (request.getSlug() != null && !request.getSlug().trim().isEmpty())
                ? SlugUtils.toSlug(request.getSlug())
                : SlugUtils.toSlug(request.getName());

        return Project.builder()
                .name(request.getName())
                .slug(slug)
                .shortDescription(request.getShortDescription())
                .description(request.getDescription())
                .category(request.getCategory() != null ? request.getCategory().toUpperCase() : "WEB_APPLICATIONS")
                .projectType(request.getProjectType())
                .featuredImage(request.getFeaturedImage())
                .galleryImages(request.getGalleryImages() != null ? request.getGalleryImages() : new ArrayList<>())
                .technologies(request.getTechnologies() != null ? request.getTechnologies() : new ArrayList<>())
                .projectUrl(request.getProjectUrl())
                .githubUrl(request.getGithubUrl())
                .caseStudyUrl(request.getCaseStudyUrl())
                .status(request.getStatus() != null ? request.getStatus().toUpperCase() : "DRAFT")
                .featured(request.getFeatured() != null && request.getFeatured())
                .displayOrder(request.getDisplayOrder())
                .seoTitle(request.getSeoTitle())
                .seoDescription(request.getSeoDescription())
                .build();
    }

    // BLOG
    public BlogResponse toBlogResponse(Blog blog) {
        if (blog == null)
            return null;
        return BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .slug(blog.getSlug())
                .excerpt(blog.getExcerpt())
                .content(blog.getContent())
                .featuredImage(blog.getFeaturedImage())
                .category(blog.getCategory())
                .author(blog.getAuthor())
                .tags(blog.getTags() != null ? blog.getTags() : new ArrayList<>())
                .status(blog.getStatus())
                .publishedAt(blog.getPublishedAt())
                .readTime(SlugUtils.calculateReadingTime(blog.getContent()))
                .seoTitle(blog.getSeoTitle())
                .seoDescription(blog.getSeoDescription())
                .createdAt(blog.getCreatedAt())
                .updatedAt(blog.getUpdatedAt())
                .build();
    }

    public Blog toBlog(BlogRequest request) {
        if (request == null)
            return null;
        String slug = (request.getSlug() != null && !request.getSlug().trim().isEmpty())
                ? SlugUtils.toSlug(request.getSlug())
                : SlugUtils.toSlug(request.getTitle());

        String status = request.getStatus() != null ? request.getStatus().toUpperCase() : "DRAFT";
        Instant publishedAt = request.getPublishedAt();
        if ("PUBLISHED".equals(status) && publishedAt == null) {
            publishedAt = Instant.now();
        }

        return Blog.builder()
                .title(request.getTitle())
                .slug(slug)
                .excerpt(request.getExcerpt())
                .content(request.getContent())
                .featuredImage(request.getFeaturedImage())
                .category(request.getCategory())
                .author(request.getAuthor())
                .tags(request.getTags() != null ? request.getTags() : new ArrayList<>())
                .status(status)
                .publishedAt(publishedAt)
                .seoTitle(request.getSeoTitle())
                .seoDescription(request.getSeoDescription())
                .build();
    }

    // INQUIRY
    public ContactInquiryResponse toInquiryResponse(ContactInquiry inquiry) {
        if (inquiry == null)
            return null;
        return ContactInquiryResponse.builder()
                .id(inquiry.getId())
                .fullName(inquiry.getFullName())
                .email(inquiry.getEmail())
                .phone(inquiry.getPhone())
                .companyName(inquiry.getCompanyName())
                .service(inquiry.getService())
                .budget(inquiry.getBudget())
                .message(inquiry.getMessage())
                .status(inquiry.getStatus())
                .createdAt(inquiry.getCreatedAt())
                .updatedAt(inquiry.getUpdatedAt())
                .build();
    }

    public ContactInquiry toContactInquiry(ContactInquiryRequest request) {
        if (request == null)
            return null;
        return ContactInquiry.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .companyName(request.getCompanyName())
                .service(request.getService())
                .budget(request.getBudget())
                .message(request.getMessage())
                .status("NEW")
                .build();
    }

    // SITE SETTINGS
    public SiteSettingsResponse toSiteSettingsResponse(SiteSettings settings) {
        if (settings == null)
            return null;
        return SiteSettingsResponse.builder()
                .id(settings.getId())
                .contactEmail(settings.getContactEmail())
                .phone(settings.getPhone())
                .whatsapp(settings.getWhatsapp())
                .location(settings.getLocation())
                .address(settings.getAddress())
                .workingHours(settings.getWorkingHours())
                .footerDescription(settings.getFooterDescription())
                .copyrightText(settings.getCopyrightText())
                .linkedin(settings.getLinkedin())
                .github(settings.getGithub())
                .instagram(settings.getInstagram())
                .updatedAt(settings.getUpdatedAt())
                .build();
    }

    public void updateSiteSettings(SiteSettings entity, SiteSettingsRequest request) {
        if (entity == null || request == null)
            return;
        if (request.getContactEmail() != null)
            entity.setContactEmail(request.getContactEmail());
        if (request.getPhone() != null)
            entity.setPhone(request.getPhone());
        if (request.getWhatsapp() != null)
            entity.setWhatsapp(request.getWhatsapp());
        if (request.getLocation() != null)
            entity.setLocation(request.getLocation());
        if (request.getAddress() != null)
            entity.setAddress(request.getAddress());
        if (request.getWorkingHours() != null)
            entity.setWorkingHours(request.getWorkingHours());
        if (request.getFooterDescription() != null)
            entity.setFooterDescription(request.getFooterDescription());
        if (request.getCopyrightText() != null)
            entity.setCopyrightText(request.getCopyrightText());
        if (request.getLinkedin() != null)
            entity.setLinkedin(request.getLinkedin());
        if (request.getGithub() != null)
            entity.setGithub(request.getGithub());
        if (request.getInstagram() != null)
            entity.setInstagram(request.getInstagram());
    }
}
