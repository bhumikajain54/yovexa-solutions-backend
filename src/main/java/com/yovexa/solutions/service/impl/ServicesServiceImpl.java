package com.yovexa.solutions.service.impl;

import com.yovexa.solutions.dto.service.ServiceRequest;
import com.yovexa.solutions.dto.service.ServiceResponse;
import com.yovexa.solutions.exception.DuplicateResourceException;
import com.yovexa.solutions.exception.ResourceNotFoundException;
import com.yovexa.solutions.mapper.EntityMapper;
import com.yovexa.solutions.model.Service;
import com.yovexa.solutions.repository.ServiceRepository;
import com.yovexa.solutions.service.ServicesService;
import com.yovexa.solutions.util.SlugUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServicesServiceImpl implements ServicesService {

    private final ServiceRepository serviceRepository;
    private final EntityMapper mapper;

    @Override
    public List<ServiceResponse> getAllServices() {
        return serviceRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(mapper::toServiceResponse)
                .toList();
    }

    @Override
    public List<ServiceResponse> getActiveServices() {
        return serviceRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(mapper::toServiceResponse)
                .toList();
    }

    @Override
    public ServiceResponse getServiceById(String id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service", "id", id));
        return mapper.toServiceResponse(service);
    }

    @Override
    public ServiceResponse getServiceBySlug(String slug) {
        Service service = serviceRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Service", "slug", slug));
        return mapper.toServiceResponse(service);
    }

    @Override
    public ServiceResponse createService(ServiceRequest request) {
        Service service = mapper.toService(request);

        if (serviceRepository.existsBySlug(service.getSlug())) {
            service.setSlug(service.getSlug() + "-" + System.currentTimeMillis());
        }

        Service saved = serviceRepository.save(service);
        return mapper.toServiceResponse(saved);
    }

    @Override
    public ServiceResponse updateService(String id, ServiceRequest request) {
        Service existing = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service", "id", id));

        existing.setTitle(request.getTitle());

        String targetSlug = (request.getSlug() != null && !request.getSlug().trim().isEmpty())
                ? SlugUtils.toSlug(request.getSlug())
                : existing.getSlug();

        if (!existing.getSlug().equals(targetSlug) && serviceRepository.existsBySlug(targetSlug)) {
            throw new DuplicateResourceException("Service", "slug", targetSlug);
        }
        existing.setSlug(targetSlug);

        existing.setShortDescription(request.getShortDescription());
        existing.setDescription(request.getDescription());
        existing.setIcon(request.getIcon());
        existing.setDisplayOrder(request.getDisplayOrder());

        if (request.getIsActive() != null) {
            existing.setActive(request.getIsActive());
        }

        Service saved = serviceRepository.save(existing);
        return mapper.toServiceResponse(saved);
    }

    @Override
    public void deleteService(String id) {
        if (!serviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Service", "id", id);
        }
        serviceRepository.deleteById(id);
    }
}
