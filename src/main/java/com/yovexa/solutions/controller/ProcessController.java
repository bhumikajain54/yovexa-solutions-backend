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
    @Operation(summary = "Get active process steps (Public)", description = "Retrieves all active process steps sorted by displayOrder ASC for the website workflow section.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of active process steps retrieved successfully")
    })
    public ResponseEntity<ApiResponse<List<ProcessStepResponse>>> getActiveProcessSteps() {
        List<ProcessStepResponse> steps = processService.getActiveProcessSteps();
        return ResponseEntity.ok(ApiResponse.success(steps));
    }

    // Admin Endpoints
    @GetMapping("/api/admin/process")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "List all process steps (Admin)", description = "Retrieves all process steps for administration.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All process steps retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - JWT required"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Admin role required")
    })
    public ResponseEntity<ApiResponse<List<ProcessStepResponse>>> getAllProcessSteps() {
        List<ProcessStepResponse> steps = processService.getAllProcessSteps();
        return ResponseEntity.ok(ApiResponse.success(steps));
    }

    @GetMapping("/api/admin/process/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get process step by ID (Admin)", description = "Fetches a process step by its MongoDB ID.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Process step retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - JWT required"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Process step not found")
    })
    public ResponseEntity<ApiResponse<ProcessStepResponse>> getProcessStepById(
            @io.swagger.v3.oas.annotations.Parameter(description = "Process step unique MongoDB ID", required = true)
            @PathVariable String id) {
        ProcessStepResponse step = processService.getProcessStepById(id);
        return ResponseEntity.ok(ApiResponse.success(step));
    }

    @PostMapping("/api/admin/process")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create process step (Admin)", description = "Creates a new process step.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Process step created successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed - missing title or stepNumber"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - JWT required")
    })
    public ResponseEntity<ApiResponse<ProcessStepResponse>> createProcessStep(
            @Valid @org.springframework.web.bind.annotation.RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New process step details", required = true)
            ProcessStepRequest request) {
        ProcessStepResponse step = processService.createProcessStep(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Process step created successfully", step));
    }

    @PutMapping("/api/admin/process/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update process step (Admin)", description = "Updates an existing process step by ID.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Process step updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - JWT required"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Process step not found")
    })
    public ResponseEntity<ApiResponse<ProcessStepResponse>> updateProcessStep(
            @io.swagger.v3.oas.annotations.Parameter(description = "Process step unique MongoDB ID", required = true)
            @PathVariable String id,
            @Valid @org.springframework.web.bind.annotation.RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated process step details", required = true)
            ProcessStepRequest request) {
        ProcessStepResponse step = processService.updateProcessStep(id, request);
        return ResponseEntity.ok(ApiResponse.success("Process step updated successfully", step));
    }

    @DeleteMapping("/api/admin/process/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete process step (Admin)", description = "Deletes a process step by ID.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Process step deleted successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - JWT required"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Process step not found")
    })
    public ResponseEntity<ApiResponse<Void>> deleteProcessStep(
            @io.swagger.v3.oas.annotations.Parameter(description = "Process step unique MongoDB ID", required = true)
            @PathVariable String id) {
        processService.deleteProcessStep(id);
        return ResponseEntity.ok(ApiResponse.successMessage("Process step deleted successfully"));
    }
}
