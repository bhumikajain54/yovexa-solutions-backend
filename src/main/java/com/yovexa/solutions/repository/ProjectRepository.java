package com.yovexa.solutions.repository;

import com.yovexa.solutions.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    Optional<Project> findBySlug(String slug);
    boolean existsBySlug(String slug);
    boolean existsBySlugAndIdNot(String slug, String id);

    List<Project> findByStatusOrderByDisplayOrderAsc(String status);
    List<Project> findByStatusAndCategoryOrderByDisplayOrderAsc(String status, String category);

    long countByStatus(String status);

    @Query("{ $and: [ " +
            "?#{ [0] == null || [0].isEmpty() ? { '_id': { '$exists': true } } : { 'name': { $regex: [0], $options: 'i' } } }, " +
            "?#{ [1] == null || [1].isEmpty() || [1] == 'ALL' ? { '_id': { '$exists': true } } : { 'category': [1] } }, " +
            "?#{ [2] == null || [2].isEmpty() || [2] == 'ALL' ? { '_id': { '$exists': true } } : { 'status': [2] } } " +
            "] }")
    Page<Project> searchAndFilter(String search, String category, String status, Pageable pageable);

    List<Project> findTop5ByOrderByCreatedAtDesc();
}
