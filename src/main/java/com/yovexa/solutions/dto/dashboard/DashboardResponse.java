package com.yovexa.solutions.dto.dashboard;

import com.yovexa.solutions.dto.blog.BlogResponse;
import com.yovexa.solutions.dto.inquiry.ContactInquiryResponse;
import com.yovexa.solutions.dto.project.ProjectResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private long projects;
    private long publishedProjects;
    private long services;
    private long publishedBlogs;
    private long draftBlogs;
    private long inquiries;
    private long newInquiries;

    private List<BlogResponse> recentBlogs;
    private List<ProjectResponse> recentProjects;
    private List<ContactInquiryResponse> recentInquiries;
}
