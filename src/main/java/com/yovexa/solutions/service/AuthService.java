package com.yovexa.solutions.service;

import com.yovexa.solutions.dto.admin.AdminResponse;
import com.yovexa.solutions.dto.auth.LoginRequest;
import com.yovexa.solutions.dto.auth.LoginResponse;
import com.yovexa.solutions.dto.auth.RegisterRequest;

public interface AuthService {
    AdminResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    void logout();
}

