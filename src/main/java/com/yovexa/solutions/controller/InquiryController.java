package com.yovexa.solutions.controller;

import com.yovexa.solutions.dto.common.ApiResponse;
import com.yovexa.solutions.dto.common.PagedResponse;
import com.yovexa.solutions.dto.inquiry.ContactInquiryRequest;
import com.yovexa.solutions.dto.inquiry.ContactInquiryResponse;
import com.yovexa.solutions.dto.inquiry.InquiryStatusUpdateRequest;
import com.yovexa.solutions.service.InquiryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Inquiries", description = "Contact Inquiries & Lead Management APIs")
public class InquiryController {

    private final InquiryService inquiryService;

    // Public Endpoint
    @PostMapping("/api/inquiries")
    @Operation(summary = "Submit contact inquiry (Public)", description = "Public form submission endpoint for potential clients to get in touch with Yovexa Solutions.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Inquiry submitted successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed - name, email, or message missing/invalid")
    })
    public ResponseEntity<ApiResponse<ContactInquiryResponse>> submitInquiry(
            @Valid @org.springframework.web.bind.annotation.RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Client contact inquiry details", required = true)
            ContactInquiryRequest request) {
        ContactInquiryResponse response = inquiryService.submitInquiry(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Thank you for reaching out! We will be in touch soon.", response));
    }

    // Admin Endpoints
    @GetMapping("/api/admin/inquiries")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "List inquiries with pagination and filters (Admin)", description = "Retrieves customer inquiries with pagination, status and search filters.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Paged inquiries retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - JWT required"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Admin role required")
    })
    public ResponseEntity<ApiResponse<PagedResponse<ContactInquiryResponse>>> getAdminInquiries(
            @io.swagger.v3.oas.annotations.Parameter(description = "Search keyword in client name, email, or company", required = false)
            @RequestParam(required = false) String search,
            @io.swagger.v3.oas.annotations.Parameter(description = "Inquiry status filter (NEW, CONTACTED, IN_PROGRESS, CLOSED)", required = false)
            @RequestParam(required = false) String status,
            @io.swagger.v3.oas.annotations.Parameter(description = "Zero-indexed page number", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @io.swagger.v3.oas.annotations.Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        PagedResponse<ContactInquiryResponse> response = inquiryService.getAdminInquiries(search, status, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/api/admin/inquiries/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get inquiry by ID (Admin)", description = "Fetches a specific inquiry record by its MongoDB ID.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Inquiry retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - JWT required"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Inquiry not found")
    })
    public ResponseEntity<ApiResponse<ContactInquiryResponse>> getInquiryById(
            @io.swagger.v3.oas.annotations.Parameter(description = "Inquiry unique MongoDB ID", required = true)
            @PathVariable String id) {
        ContactInquiryResponse response = inquiryService.getInquiryById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/api/admin/inquiries/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update inquiry status (Admin)", description = "Updates status of an existing inquiry (NEW, CONTACTED, IN_PROGRESS, CLOSED).")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Inquiry status updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed - invalid status value"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - JWT required"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Inquiry not found")
    })
    public ResponseEntity<ApiResponse<ContactInquiryResponse>> updateInquiryStatus(
            @io.swagger.v3.oas.annotations.Parameter(description = "Inquiry unique MongoDB ID", required = true)
            @PathVariable String id,
            @Valid @org.springframework.web.bind.annotation.RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New inquiry status", required = true)
            InquiryStatusUpdateRequest request) {
        ContactInquiryResponse response = inquiryService.updateInquiryStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Inquiry status updated successfully", response));
    }

    @DeleteMapping("/api/admin/inquiries/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete inquiry (Admin)", description = "Deletes an inquiry record by ID.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Inquiry deleted successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - JWT required"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Inquiry not found")
    })
    public ResponseEntity<ApiResponse<Void>> deleteInquiry(
            @io.swagger.v3.oas.annotations.Parameter(description = "Inquiry unique MongoDB ID", required = true)
            @PathVariable String id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.ok(ApiResponse.successMessage("Inquiry deleted successfully"));
    }
}
