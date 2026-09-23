package com.yovexa.solutions.controller;

import com.yovexa.solutions.dto.about.AboutRequest;
import com.yovexa.solutions.dto.about.AboutResponse;
import com.yovexa.solutions.dto.common.ApiResponse;
import com.yovexa.solutions.service.AboutService;
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
@Tag(name = "About Section", description = "About Section Content Management APIs")
public class AboutController {

    private final AboutService aboutService;

    // Public Endpoint
    @GetMapping({"/api/about/active", "/api/content/about/active"})
    @Operation(summary = "Get active about section (Public)", description = "Fetches the single currently active published about section.")
    public ResponseEntity<ApiResponse<AboutResponse>> getActiveAbout() {
        AboutResponse about = aboutService.getActiveAbout();
        return ResponseEntity.ok(ApiResponse.success(about));
    }

    // Admin Endpoints
    @GetMapping({"/api/admin/about", "/api/admin/content/about"})
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "List all about records (Admin)", description = "Retrieves all about sections for administration.")
    public ResponseEntity<ApiResponse<List<AboutResponse>>> getAllAboutSections() {
        List<AboutResponse> abouts = aboutService.getAllAboutSections();
        return ResponseEntity.ok(ApiResponse.success(abouts));
    }

    @GetMapping({"/api/admin/about/{id}", "/api/admin/content/about/{id}"})
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get about section by ID (Admin)")
    public ResponseEntity<ApiResponse<AboutResponse>> getAboutById(@PathVariable String id) {
        AboutResponse about = aboutService.getAboutById(id);
        return ResponseEntity.ok(ApiResponse.success(about));
    }

    @PostMapping({"/api/admin/about", "/api/admin/content/about"})
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create about section (Admin)")
    public ResponseEntity<ApiResponse<AboutResponse>> createAbout(@Valid @RequestBody AboutRequest request) {
        AboutResponse about = aboutService.createAbout(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("About section created successfully", about));
    }

    @PutMapping({"/api/admin/about/{id}", "/api/admin/content/about/{id}"})
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update about section (Admin)")
    public ResponseEntity<ApiResponse<AboutResponse>> updateAbout(
            @PathVariable String id,
            @Valid @RequestBody AboutRequest request) {
        AboutResponse about = aboutService.updateAbout(id, request);
        return ResponseEntity.ok(ApiResponse.success("About section updated successfully", about));
    }

    @DeleteMapping({"/api/admin/about/{id}", "/api/admin/content/about/{id}"})
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete about section (Admin)")
    public ResponseEntity<ApiResponse<Void>> deleteAbout(@PathVariable String id) {
        aboutService.deleteAbout(id);
        return ResponseEntity.ok(ApiResponse.successMessage("About section deleted successfully"));
    }
}
