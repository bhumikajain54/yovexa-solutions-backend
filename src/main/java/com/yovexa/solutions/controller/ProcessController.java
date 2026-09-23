package com.yovexa.solutions.controller;

import com.yovexa.solutions.dto.common.ApiResponse;
import com.yovexa.solutions.dto.process.ProcessStepRequest;
import com.yovexa.solutions.dto.process.ProcessStepResponse;
import com.yovexa.solutions.service.ProcessService;
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
@Tag(name = "Process", description = "How It Works / Process Steps Management APIs")
public class ProcessController {

    private final ProcessService processService;

    // Public Endpoint
    @GetMapping("/api/process")
    @Operation(summary = "Get active process steps (Public)", description = "Retrieves all active process steps sorted by displayOrder ASC.")
    public ResponseEntity<ApiResponse<List<ProcessStepResponse>>> getActiveProcessSteps() {
        List<ProcessStepResponse> steps = processService.getActiveProcessSteps();
        return ResponseEntity.ok(ApiResponse.success(steps));
    }

    // Admin Endpoints
    @GetMapping("/api/admin/process")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "List all process steps (Admin)")
    public ResponseEntity<ApiResponse<List<ProcessStepResponse>>> getAllProcessSteps() {
        List<ProcessStepResponse> steps = processService.getAllProcessSteps();
        return ResponseEntity.ok(ApiResponse.success(steps));
    }

    @GetMapping("/api/admin/process/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get process step by ID (Admin)")
    public ResponseEntity<ApiResponse<ProcessStepResponse>> getProcessStepById(@PathVariable String id) {
        ProcessStepResponse step = processService.getProcessStepById(id);
        return ResponseEntity.ok(ApiResponse.success(step));
    }

    @PostMapping("/api/admin/process")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create process step (Admin)")
    public ResponseEntity<ApiResponse<ProcessStepResponse>> createProcessStep(@Valid @RequestBody ProcessStepRequest request) {
        ProcessStepResponse step = processService.createProcessStep(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Process step created successfully", step));
    }

    @PutMapping("/api/admin/process/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update process step (Admin)")
    public ResponseEntity<ApiResponse<ProcessStepResponse>> updateProcessStep(
            @PathVariable String id,
            @Valid @RequestBody ProcessStepRequest request) {
        ProcessStepResponse step = processService.updateProcessStep(id, request);
        return ResponseEntity.ok(ApiResponse.success("Process step updated successfully", step));
    }

    @DeleteMapping("/api/admin/process/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete process step (Admin)")
    public ResponseEntity<ApiResponse<Void>> deleteProcessStep(@PathVariable String id) {
        processService.deleteProcessStep(id);
        return ResponseEntity.ok(ApiResponse.successMessage("Process step deleted successfully"));
    }
}
