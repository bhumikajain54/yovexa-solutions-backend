package com.yovexa.solutions.dto.inquiry;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactInquiryRequest {

    @NotBlank(message = "Full name is required")
    @JsonAlias("name")
    @Schema(description = "Prospect / client full name", example = "Jane Doe")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Prospect email address", example = "jane.doe@example.com")
    private String email;

    @Schema(description = "Prospect phone number", example = "+1 (555) 234-5678")
    private String phone;

    @JsonAlias("company")
    @Schema(description = "Company or organization name", example = "Acme Global Corp")
    private String companyName;

    @JsonAlias("serviceRequired")
    @Schema(description = "Desired service category or solution", example = "Custom Web Application")
    private String service;

    @JsonAlias("projectBudget")
    @Schema(description = "Estimated project budget range", example = "$10k - $25k")
    private String budget;

    @NotBlank(message = "Message is required")
    @Schema(description = "Project overview and requirements message", example = "We are seeking a modern, high-performance web platform for our financial services.")
    private String message;
}
