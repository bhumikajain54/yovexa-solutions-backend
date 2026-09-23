package com.yovexa.solutions.dto.inquiry;

import com.fasterxml.jackson.annotation.JsonAlias;
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
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String phone;

    @JsonAlias("company")
    private String companyName;

    @JsonAlias("serviceRequired")
    private String service;

    @JsonAlias("projectBudget")
    private String budget;

    @NotBlank(message = "Message is required")
    private String message;
}
