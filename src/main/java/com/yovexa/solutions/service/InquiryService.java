package com.yovexa.solutions.service;

import com.yovexa.solutions.dto.common.PagedResponse;
import com.yovexa.solutions.dto.inquiry.ContactInquiryRequest;
import com.yovexa.solutions.dto.inquiry.ContactInquiryResponse;
import com.yovexa.solutions.dto.inquiry.InquiryStatusUpdateRequest;

public interface InquiryService {
    ContactInquiryResponse submitInquiry(ContactInquiryRequest request);
    PagedResponse<ContactInquiryResponse> getAdminInquiries(String search, String status, int page, int size);
    ContactInquiryResponse getInquiryById(String id);
    ContactInquiryResponse updateInquiryStatus(String id, InquiryStatusUpdateRequest request);
    void deleteInquiry(String id);
}
