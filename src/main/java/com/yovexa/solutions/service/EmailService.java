package com.yovexa.solutions.service;

import com.yovexa.solutions.model.ContactInquiry;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
    void sendInquiryNotification(ContactInquiry inquiry);
}
