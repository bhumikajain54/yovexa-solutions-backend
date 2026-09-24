package com.yovexa.solutions.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health Check", description = "Endpoints for deployment and service health verification")
public class HealthController {

    @GetMapping
    @Operation(summary = "Check backend service health", description = "Returns UP status for deployment verification without exposing sensitive details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application is running and healthy")
    })
    public ResponseEntity<Map<String, String>> checkHealth() {
        return ResponseEntity.ok(Collections.singletonMap("status", "UP"));
    }
}
