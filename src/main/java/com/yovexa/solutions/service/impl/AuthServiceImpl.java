package com.yovexa.solutions.service.impl;

import com.yovexa.solutions.dto.admin.AdminResponse;
import com.yovexa.solutions.dto.auth.LoginRequest;
import com.yovexa.solutions.dto.auth.LoginResponse;
import com.yovexa.solutions.dto.auth.RegisterRequest;
import com.yovexa.solutions.dto.auth.UserSummaryDto;
import com.yovexa.solutions.exception.DuplicateResourceException;
import com.yovexa.solutions.exception.ForbiddenException;
import com.yovexa.solutions.exception.UnauthorizedException;
import com.yovexa.solutions.mapper.EntityMapper;
import com.yovexa.solutions.model.Admin;
import com.yovexa.solutions.repository.AdminRepository;
import com.yovexa.solutions.security.JwtService;
import com.yovexa.solutions.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EntityMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminResponse register(RegisterRequest request) {
        // Enforce first-admin registration only: if any admin exists, public registration is forbidden
        if (adminRepository.count() > 0) {
            throw new ForbiddenException("Admin registration is currently disabled.");
        }

        String email = request.getEmail().trim().toLowerCase();

        if (adminRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateResourceException("An account with this email already exists.");
        }

        if (request.getConfirmPassword() != null && !request.getConfirmPassword().isBlank()) {
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                throw new IllegalArgumentException("Passwords do not match.");
            }
        }

        Admin admin = Admin.builder()
                .name(request.getName().trim())
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ADMIN")
                .isActive(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Admin savedAdmin = adminRepository.save(admin);
        return mapper.toAdminResponse(savedAdmin);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        Admin admin = adminRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password."));

        if (Boolean.FALSE.equals(admin.getIsActive())) {
            throw new UnauthorizedException("Your account is inactive. Please contact an administrator.");
        }

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new UnauthorizedException("Invalid email or password.");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, request.getPassword())
            );
        } catch (DisabledException ex) {
            throw new UnauthorizedException("Your account is inactive. Please contact an administrator.");
        } catch (BadCredentialsException | org.springframework.security.authentication.InternalAuthenticationServiceException ex) {
            throw new UnauthorizedException("Invalid email or password.");
        } catch (org.springframework.security.core.AuthenticationException ex) {
            throw new UnauthorizedException("Invalid email or password.");
        }

        String token = jwtService.generateToken(admin);
        long expiresIn = jwtService.getExpirationInSeconds();
        UserSummaryDto userSummary = mapper.toUserSummaryDto(admin);

        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .user(userSummary)
                .build();
    }

    @Override
    public void logout() {
        // Stateless JWT logout - token cleared on frontend client
    }
}

