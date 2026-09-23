package com.yovexa.solutions.repository;

import com.yovexa.solutions.model.ContactInquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactInquiryRepository extends MongoRepository<ContactInquiry, String> {
    long countByStatus(String status);

    @Query("{ $and: [ " +
            "?#{ [0] == null || [0].isEmpty() ? { $expr: { $eq: [1, 1] } } : { $or: [ { 'fullName': { $regex: [0], $options: 'i' } }, { 'email': { $regex: [0], $options: 'i' } }, { 'companyName': { $regex: [0], $options: 'i' } } ] } }, " +
            "?#{ [1] == null || [1].isEmpty() || [1] == 'ALL' ? { $expr: { $eq: [1, 1] } } : { 'status': [1] } } " +
            "] }")
    Page<ContactInquiry> searchAndFilter(String search, String status, Pageable pageable);

    List<ContactInquiry> findTop5ByOrderByCreatedAtDesc();
}
