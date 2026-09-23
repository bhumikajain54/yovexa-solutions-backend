package com.yovexa.solutions.repository;

import com.yovexa.solutions.model.Service;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends MongoRepository<Service, String> {
    List<Service> findByIsActiveTrueOrderByDisplayOrderAsc();
    List<Service> findAllByOrderByDisplayOrderAsc();
    Optional<Service> findBySlug(String slug);
    boolean existsBySlug(String slug);
    long countByIsActiveTrue();
}
