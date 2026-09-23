package com.yovexa.solutions.controller;

import com.yovexa.solutions.dto.common.ApiResponse;
import com.yovexa.solutions.dto.settings.SiteSettingsRequest;
import com.yovexa.solutions.dto.settings.SiteSettingsResponse;
import com.yovexa.solutions.service.SiteSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Site Settings", description = "Global Site Contact, Footer & Social Settings APIs")
public class SiteSettingsController {

    private final SiteSettingsService siteSettingsService;

    // Public Endpoints
    @GetMapping({"/api/site-settings", "/api/content/settings", "/api/content/footer", "/api/content/contact"})
    @Operation(summary = "Get site settings (Public)", description = "Retrieves singleton site contact, footer and social settings.")
    public ResponseEntity<ApiResponse<SiteSettingsResponse>> getSettings() {
        SiteSettingsResponse settings = siteSettingsService.getSettings();
        return ResponseEntity.ok(ApiResponse.success(settings));
    }

    // Admin Endpoints
    @GetMapping({"/api/admin/site-settings", "/api/admin/content/settings", "/api/admin/content/footer", "/api/admin/content/contact"})
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get site settings (Admin)")
    public ResponseEntity<ApiResponse<SiteSettingsResponse>> getAdminSettings() {
        SiteSettingsResponse settings = siteSettingsService.getSettings();
        return ResponseEntity.ok(ApiResponse.success(settings));
    }

    @PutMapping({"/api/admin/site-settings", "/api/admin/content/settings", "/api/admin/content/footer", "/api/admin/content/contact"})
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update site settings (Admin)")
    public ResponseEntity<ApiResponse<SiteSettingsResponse>> updateSettings(@RequestBody SiteSettingsRequest request) {
        SiteSettingsResponse settings = siteSettingsService.updateSettings(request);
        return ResponseEntity.ok(ApiResponse.success("Site settings updated successfully", settings));
    }
}
