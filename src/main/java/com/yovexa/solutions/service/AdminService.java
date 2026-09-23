package com.yovexa.solutions.service;

import com.yovexa.solutions.dto.admin.*;

import java.util.List;

public interface AdminService {
    AdminProfileResponse getProfile(String email);
    void changePassword(String email, ChangePasswordRequest request);
    List<AdminResponse> getAllAdmins();
    AdminResponse getAdminById(String id);
    AdminResponse createAdmin(CreateAdminRequest request);
    AdminResponse updateAdmin(String id, UpdateAdminRequest request);
    void deleteAdmin(String id, String authenticatedAdminEmail);
}
