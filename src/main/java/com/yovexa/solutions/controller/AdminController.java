package com.yovexa.solutions.controller;

import com.yovexa.solutions.dto.admin.*;
import com.yovexa.solutions.dto.common.ApiResponse;
import com.yovexa.solutions.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Admin Management", description = "Admin CRUD, Profile & Password APIs")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/profile")
    @Operation(summary = "Get admin profile", description = "Returns currently logged-in administrator's profile details.")
    public ResponseEntity<ApiResponse<AdminProfileResponse>> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        AdminProfileResponse profile = adminService.getProfile(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Profile fetched successfully.", profile));
    }

    @PutMapping("/profile/password")
    @Operation(summary = "Change password", description = "Updates password for currently logged-in administrator.")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        adminService.changePassword(userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.successMessage("Password changed successfully."));
    }

    @GetMapping("/admins")
    @Operation(summary = "List all administrators", description = "Fetches list of all administrator accounts.")
    public ResponseEntity<ApiResponse<List<AdminResponse>>> getAllAdmins() {
        List<AdminResponse> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(ApiResponse.success("Administrators fetched successfully.", admins));
    }

    @GetMapping("/admins/{id}")
    @Operation(summary = "Get administrator by ID", description = "Fetches a single administrator by unique ID.")
    public ResponseEntity<ApiResponse<AdminResponse>> getAdminById(@PathVariable String id) {
        AdminResponse admin = adminService.getAdminById(id);
        return ResponseEntity.ok(ApiResponse.success("Administrator fetched successfully.", admin));
    }

    @PostMapping("/admins")
    @Operation(summary = "Create an administrator", description = "Creates a new administrator account (Requires ADMIN authentication).")
    public ResponseEntity<ApiResponse<AdminResponse>> createAdmin(@Valid @RequestBody CreateAdminRequest request) {
        AdminResponse response = adminService.createAdmin(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Administrator created successfully.", response));
    }

    @PutMapping("/admins/{id}")
    @Operation(summary = "Update administrator", description = "Updates administrator name, email, or active status.")
    public ResponseEntity<ApiResponse<AdminResponse>> updateAdmin(
            @PathVariable String id,
            @Valid @RequestBody UpdateAdminRequest request
    ) {
        AdminResponse response = adminService.updateAdmin(id, request);
        return ResponseEntity.ok(ApiResponse.success("Administrator updated successfully.", response));
    }

    @DeleteMapping("/admins/{id}")
    @Operation(summary = "Delete administrator", description = "Deletes an administrator account. Prevents deleting the last remaining administrator.")
    public ResponseEntity<ApiResponse<Void>> deleteAdmin(
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        adminService.deleteAdmin(id, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.successMessage("Administrator deleted successfully."));
    }
}
