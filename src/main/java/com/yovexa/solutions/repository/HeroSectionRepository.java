package com.yovexa.solutions.repository;

import com.yovexa.solutions.model.HeroSection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeroSectionRepository extends MongoRepository<HeroSection, String> {
    Optional<HeroSection> findFirstByIsActiveTrue();
    Optional<HeroSection> findFirstByStatusOrderByCreatedAtDesc(String status);
    List<HeroSection> findAllByOrderByCreatedAtDesc();
}
