package com.yovexa.solutions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "contact_inquiries")
public class ContactInquiry {

    @Id
    private String id;

    @Indexed
    private String fullName;

    @Indexed
    private String email;

    private String phone;
    private String companyName;
    private String service;
    private String budget;
    private String message;

    @Builder.Default
    @Indexed
    private String status = "NEW"; // NEW, CONTACTED, IN_PROGRESS, CLOSED

    @CreatedDate
    @Indexed
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
