package com.yovexa.solutions.repository;

import com.yovexa.solutions.model.ProcessStep;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessStepRepository extends MongoRepository<ProcessStep, String> {
    List<ProcessStep> findByIsActiveTrueOrderByDisplayOrderAsc();
    List<ProcessStep> findAllByOrderByDisplayOrderAsc();
}
