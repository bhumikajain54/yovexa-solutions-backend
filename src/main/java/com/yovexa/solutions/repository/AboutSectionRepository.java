package com.yovexa.solutions.repository;

import com.yovexa.solutions.model.AboutSection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AboutSectionRepository extends MongoRepository<AboutSection, String> {
    Optional<AboutSection> findFirstByIsActiveTrue();
    Optional<AboutSection> findFirstByStatusOrderByCreatedAtDesc(String status);
    List<AboutSection> findAllByOrderByCreatedAtDesc();
}
