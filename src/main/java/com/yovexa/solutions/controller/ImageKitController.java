package com.yovexa.solutions.controller;

import com.yovexa.solutions.dto.imagekit.ImageKitAuthResponse;
import com.yovexa.solutions.service.ImageKitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/imagekit")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "ImageKit", description = "ImageKit Media & Upload Authentication APIs")
public class ImageKitController {

    private final ImageKitService imageKitService;

    @GetMapping("/auth")
    @Operation(summary = "Get ImageKit upload authentication parameters (Admin only)",
            description = "Returns upload token, expire timestamp, HMAC-SHA1 signature, and public key for client-side ImageKit uploads. Requires ADMIN privileges.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "ImageKit auth parameters retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - Valid admin JWT required"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Admin role required")
    })
    public ResponseEntity<ImageKitAuthResponse> getAuthParameters() {
        ImageKitAuthResponse authResponse = imageKitService.getAuthenticationParameters();
        return ResponseEntity.ok(authResponse);
    }
}
