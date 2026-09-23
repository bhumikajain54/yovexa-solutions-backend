package com.yovexa.solutions.service;

import com.yovexa.solutions.dto.service.ServiceRequest;
import com.yovexa.solutions.dto.service.ServiceResponse;

import java.util.List;

public interface ServicesService {
    List<ServiceResponse> getAllServices();
    List<ServiceResponse> getActiveServices();
    ServiceResponse getServiceById(String id);
    ServiceResponse getServiceBySlug(String slug);
    ServiceResponse createService(ServiceRequest request);
    ServiceResponse updateService(String id, ServiceRequest request);
    void deleteService(String id);
}
