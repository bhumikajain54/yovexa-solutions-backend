package com.yovexa.solutions.repository;

import com.yovexa.solutions.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends MongoRepository<Blog, String> {
    Optional<Blog> findBySlug(String slug);
    boolean existsBySlug(String slug);
    boolean existsBySlugAndIdNot(String slug, String id);

    List<Blog> findByStatusOrderByPublishedAtDesc(String status);
    List<Blog> findByStatusAndCategoryOrderByPublishedAtDesc(String status, String category);

    long countByStatus(String status);

    @Query("{ $and: [ " +
            "?#{ [0] == null || [0].isEmpty() ? { $expr: { $eq: [1, 1] } } : { 'title': { $regex: [0], $options: 'i' } } }, " +
            "?#{ [1] == null || [1].isEmpty() || [1] == 'ALL' ? { $expr: { $eq: [1, 1] } } : { 'category': [1] } }, " +
            "?#{ [2] == null || [2].isEmpty() || [2] == 'ALL' ? { $expr: { $eq: [1, 1] } } : { 'status': [2] } } " +
            "] }")
    Page<Blog> searchAndFilter(String search, String category, String status, Pageable pageable);

    @Query("{ $and: [ " +
            "{ 'status': 'PUBLISHED' }, " +
            "?#{ [0] == null || [0].isEmpty() ? { $expr: { $eq: [1, 1] } } : { $or: [ { 'title': { $regex: [0], $options: 'i' } }, { 'excerpt': { $regex: [0], $options: 'i' } } ] } }, " +
            "?#{ [1] == null || [1].isEmpty() || [1] == 'ALL' ? { $expr: { $eq: [1, 1] } } : { 'category': [1] } } " +
            "] }")
    Page<Blog> searchPublic(String search, String category, Pageable pageable);

    List<Blog> findTop5ByOrderByCreatedAtDesc();
}
