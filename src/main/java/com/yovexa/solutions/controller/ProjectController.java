package com.yovexa.solutions.controller;

import com.yovexa.solutions.dto.common.ApiResponse;
import com.yovexa.solutions.dto.common.PagedResponse;
import com.yovexa.solutions.dto.project.ProjectRequest;
import com.yovexa.solutions.dto.project.ProjectResponse;
import com.yovexa.solutions.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Projects / Portfolio", description = "Portfolio and Case Studies Management APIs")
public class ProjectController {

    private final ProjectService projectService;

    // Public Endpoints
    @GetMapping("/api/projects")
    @Operation(summary = "Get published projects (Public)", description = "Retrieves published projects with optional category and search filters.")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getPublicProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        List<ProjectResponse> projects = projectService.getPublicProjects(category, search);
        return ResponseEntity.ok(ApiResponse.success(projects));
    }

    @GetMapping("/api/projects/{slug}")
    @Operation(summary = "Get published project by slug (Public)")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectBySlug(@PathVariable String slug) {
        ProjectResponse project = projectService.getProjectBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(project));
    }

    // Admin Endpoints
    @GetMapping("/api/admin/projects")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "List all projects with pagination and filters (Admin)")
    public ResponseEntity<ApiResponse<PagedResponse<ProjectResponse>>> getAdminProjects(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponse<ProjectResponse> response = projectService.getAdminProjects(search, category, status, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/api/admin/projects/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get project by ID (Admin)")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(@PathVariable String id) {
        ProjectResponse project = projectService.getProjectById(id);
        return ResponseEntity.ok(ApiResponse.success(project));
    }

    @PostMapping("/api/admin/projects")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create project (Admin)")
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(@Valid @RequestBody ProjectRequest request) {
        ProjectResponse project = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Project created successfully", project));
    }

    @PutMapping("/api/admin/projects/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update project (Admin)")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable String id,
            @Valid @RequestBody ProjectRequest request) {
        ProjectResponse project = projectService.updateProject(id, request);
        return ResponseEntity.ok(ApiResponse.success("Project updated successfully", project));
    }

    @DeleteMapping("/api/admin/projects/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete project (Admin)")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.successMessage("Project deleted successfully"));
    }
}
