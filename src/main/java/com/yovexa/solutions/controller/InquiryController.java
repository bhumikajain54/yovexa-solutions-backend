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
@Tag(name = "Contact Inquiries", description = "Contact Inquiries & Lead Management APIs")
public class InquiryController {

    private final InquiryService inquiryService;

    // Public Endpoint
    @PostMapping("/api/inquiries")
    @Operation(summary = "Submit contact inquiry (Public)", description = "Public form submission endpoint for potential clients.")
    public ResponseEntity<ApiResponse<ContactInquiryResponse>> submitInquiry(@Valid @RequestBody ContactInquiryRequest request) {
        ContactInquiryResponse response = inquiryService.submitInquiry(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Thank you for reaching out! We will be in touch soon.", response));
    }

    // Admin Endpoints
    @GetMapping("/api/admin/inquiries")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "List inquiries with pagination and filters (Admin)")
    public ResponseEntity<ApiResponse<PagedResponse<ContactInquiryResponse>>> getAdminInquiries(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponse<ContactInquiryResponse> response = inquiryService.getAdminInquiries(search, status, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/api/admin/inquiries/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get inquiry by ID (Admin)")
    public ResponseEntity<ApiResponse<ContactInquiryResponse>> getInquiryById(@PathVariable String id) {
        ContactInquiryResponse response = inquiryService.getInquiryById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/api/admin/inquiries/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update inquiry status (Admin)")
    public ResponseEntity<ApiResponse<ContactInquiryResponse>> updateInquiryStatus(
            @PathVariable String id,
            @Valid @RequestBody InquiryStatusUpdateRequest request) {
        ContactInquiryResponse response = inquiryService.updateInquiryStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Inquiry status updated successfully", response));
    }

    @DeleteMapping("/api/admin/inquiries/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete inquiry (Admin)")
    public ResponseEntity<ApiResponse<Void>> deleteInquiry(@PathVariable String id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.ok(ApiResponse.successMessage("Inquiry deleted successfully"));
    }
}
