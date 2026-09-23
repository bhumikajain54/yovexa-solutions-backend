package com.yovexa.solutions.controller;

import com.yovexa.solutions.dto.blog.BlogRequest;
import com.yovexa.solutions.dto.blog.BlogResponse;
import com.yovexa.solutions.dto.common.ApiResponse;
import com.yovexa.solutions.dto.common.PagedResponse;
import com.yovexa.solutions.service.BlogService;
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
@Tag(name = "Blogs", description = "Blog Articles Management APIs")
public class BlogController {

    private final BlogService blogService;

    // Public Endpoints
    @GetMapping("/api/blogs")
    @Operation(summary = "Get published blogs (Public)", description = "Retrieves published blogs with optional search and category filters.")
    public ResponseEntity<ApiResponse<List<BlogResponse>>> getPublicBlogs(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category) {
        List<BlogResponse> blogs = blogService.getPublicBlogs(search, category);
        return ResponseEntity.ok(ApiResponse.success(blogs));
    }

    @GetMapping("/api/blogs/{slug}")
    @Operation(summary = "Get published blog by slug (Public)")
    public ResponseEntity<ApiResponse<BlogResponse>> getBlogBySlug(@PathVariable String slug) {
        BlogResponse blog = blogService.getBlogBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(blog));
    }

    // Admin Endpoints
    @GetMapping("/api/admin/blogs")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "List all blogs with pagination and filters (Admin)")
    public ResponseEntity<ApiResponse<PagedResponse<BlogResponse>>> getAdminBlogs(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponse<BlogResponse> response = blogService.getAdminBlogs(search, category, status, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/api/admin/blogs/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get blog by ID (Admin)")
    public ResponseEntity<ApiResponse<BlogResponse>> getBlogById(@PathVariable String id) {
        BlogResponse blog = blogService.getBlogById(id);
        return ResponseEntity.ok(ApiResponse.success(blog));
    }

    @PostMapping("/api/admin/blogs")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create blog (Admin)")
    public ResponseEntity<ApiResponse<BlogResponse>> createBlog(@Valid @RequestBody BlogRequest request) {
        BlogResponse blog = blogService.createBlog(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Blog created successfully", blog));
    }

    @PutMapping("/api/admin/blogs/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update blog (Admin)")
    public ResponseEntity<ApiResponse<BlogResponse>> updateBlog(
            @PathVariable String id,
            @Valid @RequestBody BlogRequest request) {
        BlogResponse blog = blogService.updateBlog(id, request);
        return ResponseEntity.ok(ApiResponse.success("Blog updated successfully", blog));
    }

    @DeleteMapping("/api/admin/blogs/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete blog (Admin)")
    public ResponseEntity<ApiResponse<Void>> deleteBlog(@PathVariable String id) {
        blogService.deleteBlog(id);
        return ResponseEntity.ok(ApiResponse.successMessage("Blog deleted successfully"));
    }
}
