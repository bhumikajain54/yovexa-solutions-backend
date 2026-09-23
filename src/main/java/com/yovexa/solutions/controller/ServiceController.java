package com.yovexa.solutions.controller;

import com.yovexa.solutions.dto.common.ApiResponse;
import com.yovexa.solutions.dto.service.ServiceRequest;
import com.yovexa.solutions.dto.service.ServiceResponse;
import com.yovexa.solutions.service.ServicesService;
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
@Tag(name = "Services", description = "Services Management APIs")
public class ServiceController {

    private final ServicesService servicesService;

    // Public Endpoints
    @GetMapping("/api/services")
    @Operation(summary = "Get active services (Public)", description = "Retrieves all active services sorted by displayOrder ASC.")
    public ResponseEntity<ApiResponse<List<ServiceResponse>>> getActiveServices() {
        List<ServiceResponse> services = servicesService.getActiveServices();
        return ResponseEntity.ok(ApiResponse.success(services));
    }

    @GetMapping("/api/services/{slug}")
    @Operation(summary = "Get service by slug (Public)")
    public ResponseEntity<ApiResponse<ServiceResponse>> getServiceBySlug(@PathVariable String slug) {
        ServiceResponse service = servicesService.getServiceBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(service));
    }

    // Admin Endpoints
    @GetMapping("/api/admin/services")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "List all services (Admin)")
    public ResponseEntity<ApiResponse<List<ServiceResponse>>> getAllServices() {
        List<ServiceResponse> services = servicesService.getAllServices();
        return ResponseEntity.ok(ApiResponse.success(services));
    }

    @GetMapping("/api/admin/services/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get service by ID (Admin)")
    public ResponseEntity<ApiResponse<ServiceResponse>> getServiceById(@PathVariable String id) {
        ServiceResponse service = servicesService.getServiceById(id);
        return ResponseEntity.ok(ApiResponse.success(service));
    }

    @PostMapping("/api/admin/services")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create new service (Admin)")
    public ResponseEntity<ApiResponse<ServiceResponse>> createService(@Valid @RequestBody ServiceRequest request) {
        ServiceResponse service = servicesService.createService(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Service created successfully", service));
    }

    @PutMapping("/api/admin/services/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update service (Admin)")
    public ResponseEntity<ApiResponse<ServiceResponse>> updateService(
            @PathVariable String id,
            @Valid @RequestBody ServiceRequest request) {
        ServiceResponse service = servicesService.updateService(id, request);
        return ResponseEntity.ok(ApiResponse.success("Service updated successfully", service));
    }

    @DeleteMapping("/api/admin/services/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete service (Admin)")
    public ResponseEntity<ApiResponse<Void>> deleteService(@PathVariable String id) {
        servicesService.deleteService(id);
        return ResponseEntity.ok(ApiResponse.successMessage("Service deleted successfully"));
    }
}
