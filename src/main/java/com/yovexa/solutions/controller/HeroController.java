package com.yovexa.solutions.controller;

import com.yovexa.solutions.dto.common.ApiResponse;
import com.yovexa.solutions.dto.hero.HeroRequest;
import com.yovexa.solutions.dto.hero.HeroResponse;
import com.yovexa.solutions.service.HeroService;
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
@Tag(name = "Hero Section", description = "Hero Section Content Management APIs")
public class HeroController {

    private final HeroService heroService;

    // Public Endpoint
    @GetMapping({"/api/hero/active", "/api/content/hero/active"})
    @Operation(summary = "Get active hero section (Public)", description = "Fetches the single currently active published hero section.")
    public ResponseEntity<ApiResponse<HeroResponse>> getActiveHero() {
        HeroResponse hero = heroService.getActiveHero();
        return ResponseEntity.ok(ApiResponse.success(hero));
    }

    // Admin Endpoints
    @GetMapping({"/api/admin/hero", "/api/admin/content/hero"})
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "List all hero records (Admin)", description = "Retrieves all hero sections for administration.")
    public ResponseEntity<ApiResponse<List<HeroResponse>>> getAllHeroes() {
        List<HeroResponse> heroes = heroService.getAllHeroes();
        return ResponseEntity.ok(ApiResponse.success(heroes));
    }

    @GetMapping({"/api/admin/hero/{id}", "/api/admin/content/hero/{id}"})
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get hero section by ID (Admin)")
    public ResponseEntity<ApiResponse<HeroResponse>> getHeroById(@PathVariable String id) {
        HeroResponse hero = heroService.getHeroById(id);
        return ResponseEntity.ok(ApiResponse.success(hero));
    }

    @PostMapping({"/api/admin/hero", "/api/admin/content/hero"})
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create hero section (Admin)")
    public ResponseEntity<ApiResponse<HeroResponse>> createHero(@Valid @RequestBody HeroRequest request) {
        HeroResponse hero = heroService.createHero(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Hero section created successfully", hero));
    }

    @PutMapping({"/api/admin/hero/{id}", "/api/admin/content/hero/{id}"})
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update hero section (Admin)")
    public ResponseEntity<ApiResponse<HeroResponse>> updateHero(
            @PathVariable String id,
            @Valid @RequestBody HeroRequest request) {
        HeroResponse hero = heroService.updateHero(id, request);
        return ResponseEntity.ok(ApiResponse.success("Hero section updated successfully", hero));
    }

    @DeleteMapping({"/api/admin/hero/{id}", "/api/admin/content/hero/{id}"})
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete hero section (Admin)")
    public ResponseEntity<ApiResponse<Void>> deleteHero(@PathVariable String id) {
        heroService.deleteHero(id);
        return ResponseEntity.ok(ApiResponse.successMessage("Hero section deleted successfully"));
    }
}
