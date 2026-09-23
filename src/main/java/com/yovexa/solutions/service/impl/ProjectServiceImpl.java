package com.yovexa.solutions.service.impl;

import com.yovexa.solutions.dto.common.PagedResponse;
import com.yovexa.solutions.dto.project.ProjectRequest;
import com.yovexa.solutions.dto.project.ProjectResponse;
import com.yovexa.solutions.exception.DuplicateResourceException;
import com.yovexa.solutions.exception.ResourceNotFoundException;
import com.yovexa.solutions.mapper.EntityMapper;
import com.yovexa.solutions.model.Project;
import com.yovexa.solutions.repository.ProjectRepository;
import com.yovexa.solutions.service.ProjectService;
import com.yovexa.solutions.util.SlugUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EntityMapper mapper;

    @Override
    public List<ProjectResponse> getPublicProjects(String category, String search) {
        List<Project> list;
        if (category != null && !category.trim().isEmpty() && !category.equalsIgnoreCase("ALL")) {
            list = projectRepository.findByStatusAndCategoryOrderByDisplayOrderAsc("PUBLISHED", category.toUpperCase());
        } else {
            list = projectRepository.findByStatusOrderByDisplayOrderAsc("PUBLISHED");
        }

        if (search != null && !search.trim().isEmpty()) {
            String q = search.trim().toLowerCase();
            list = list.stream()
                    .filter(p -> (p.getName() != null && p.getName().toLowerCase().contains(q)) ||
                                 (p.getShortDescription() != null && p.getShortDescription().toLowerCase().contains(q)))
                    .toList();
        }

        return list.stream().map(mapper::toProjectResponse).toList();
    }

    @Override
    public ProjectResponse getProjectBySlug(String slug) {
        Project project = projectRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "slug", slug));
        return mapper.toProjectResponse(project);
    }

    @Override
    public PagedResponse<ProjectResponse> getAdminProjects(String search, String category, String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("displayOrder").ascending().and(Sort.by("createdAt").descending()));
        Page<Project> pageResult = projectRepository.searchAndFilter(
                (search != null && !search.trim().isEmpty()) ? search.trim() : null,
                category,
                status,
                pageable
        );

        Page<ProjectResponse> dtoPage = pageResult.map(mapper::toProjectResponse);
        return PagedResponse.of(dtoPage);
    }

    @Override
    public ProjectResponse getProjectById(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        return mapper.toProjectResponse(project);
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = mapper.toProject(request);

        if (projectRepository.existsBySlug(project.getSlug())) {
            throw new DuplicateResourceException("Project", "slug", project.getSlug());
        }

        Project saved = projectRepository.save(project);
        return mapper.toProjectResponse(saved);
    }

    @Override
    public ProjectResponse updateProject(String id, ProjectRequest request) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));

        existing.setName(request.getName());

        String targetSlug = (request.getSlug() != null && !request.getSlug().trim().isEmpty())
                ? SlugUtils.toSlug(request.getSlug())
                : existing.getSlug();

        if (!existing.getSlug().equals(targetSlug) && projectRepository.existsBySlugAndIdNot(targetSlug, id)) {
            throw new DuplicateResourceException("Project", "slug", targetSlug);
        }
        existing.setSlug(targetSlug);

        existing.setShortDescription(request.getShortDescription());
        existing.setDescription(request.getDescription());
        if (request.getCategory() != null) {
            existing.setCategory(request.getCategory().toUpperCase());
        }
        existing.setProjectType(request.getProjectType());
        existing.setFeaturedImage(request.getFeaturedImage());
        if (request.getGalleryImages() != null) {
            existing.setGalleryImages(request.getGalleryImages());
        }
        if (request.getTechnologies() != null) {
            existing.setTechnologies(request.getTechnologies());
        }
        existing.setProjectUrl(request.getProjectUrl());
        existing.setGithubUrl(request.getGithubUrl());
        existing.setCaseStudyUrl(request.getCaseStudyUrl());

        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus().toUpperCase());
        }
        if (request.getFeatured() != null) {
            existing.setFeatured(request.getFeatured());
        }
        existing.setDisplayOrder(request.getDisplayOrder());
        existing.setSeoTitle(request.getSeoTitle());
        existing.setSeoDescription(request.getSeoDescription());

        Project saved = projectRepository.save(existing);
        return mapper.toProjectResponse(saved);
    }

    @Override
    public void deleteProject(String id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project", "id", id);
        }
        projectRepository.deleteById(id);
    }
}
