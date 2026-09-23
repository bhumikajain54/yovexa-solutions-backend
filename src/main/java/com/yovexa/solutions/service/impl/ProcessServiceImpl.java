package com.yovexa.solutions.service.impl;

import com.yovexa.solutions.dto.process.ProcessStepRequest;
import com.yovexa.solutions.dto.process.ProcessStepResponse;
import com.yovexa.solutions.exception.ResourceNotFoundException;
import com.yovexa.solutions.mapper.EntityMapper;
import com.yovexa.solutions.model.ProcessStep;
import com.yovexa.solutions.repository.ProcessStepRepository;
import com.yovexa.solutions.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final ProcessStepRepository processStepRepository;
    private final EntityMapper mapper;

    @Override
    public List<ProcessStepResponse> getAllProcessSteps() {
        return processStepRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(mapper::toProcessStepResponse)
                .toList();
    }

    @Override
    public List<ProcessStepResponse> getActiveProcessSteps() {
        return processStepRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(mapper::toProcessStepResponse)
                .toList();
    }

    @Override
    public ProcessStepResponse getProcessStepById(String id) {
        ProcessStep step = processStepRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProcessStep", "id", id));
        return mapper.toProcessStepResponse(step);
    }

    @Override
    public ProcessStepResponse createProcessStep(ProcessStepRequest request) {
        ProcessStep step = mapper.toProcessStep(request);
        ProcessStep saved = processStepRepository.save(step);
        return mapper.toProcessStepResponse(saved);
    }

    @Override
    public ProcessStepResponse updateProcessStep(String id, ProcessStepRequest request) {
        ProcessStep existing = processStepRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProcessStep", "id", id));

        existing.setStepNumber(request.getStepNumber());
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setIcon(request.getIcon());
        existing.setDisplayOrder(request.getDisplayOrder());

        if (request.getIsActive() != null) {
            existing.setActive(request.getIsActive());
        }

        ProcessStep saved = processStepRepository.save(existing);
        return mapper.toProcessStepResponse(saved);
    }

    @Override
    public void deleteProcessStep(String id) {
        if (!processStepRepository.existsById(id)) {
            throw new ResourceNotFoundException("ProcessStep", "id", id);
        }
        processStepRepository.deleteById(id);
    }
}
