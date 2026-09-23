package com.yovexa.solutions.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponse {
    private String id;
    private String name;
    private String email;
    private String role;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}
