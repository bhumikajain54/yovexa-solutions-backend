package com.yovexa.solutions.service;

import com.yovexa.solutions.dto.process.ProcessStepRequest;
import com.yovexa.solutions.dto.process.ProcessStepResponse;

import java.util.List;

public interface ProcessService {
    List<ProcessStepResponse> getAllProcessSteps();
    List<ProcessStepResponse> getActiveProcessSteps();
    ProcessStepResponse getProcessStepById(String id);
    ProcessStepResponse createProcessStep(ProcessStepRequest request);
    ProcessStepResponse updateProcessStep(String id, ProcessStepRequest request);
    void deleteProcessStep(String id);
}
