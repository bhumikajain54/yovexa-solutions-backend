package com.yovexa.solutions.dto.inquiry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactInquiryResponse {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String companyName;
    private String service;
    private String budget;
    private String message;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;

    public String getCompany() {
        return companyName;
    }

    public String getServiceRequired() {
        return service;
    }

    public String getProjectBudget() {
        return budget;
    }
}
