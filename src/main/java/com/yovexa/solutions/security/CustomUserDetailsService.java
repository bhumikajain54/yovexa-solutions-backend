package com.yovexa.solutions.security;

import com.yovexa.solutions.model.Admin;
import com.yovexa.solutions.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found with email: " + email));

        boolean isEnabled = admin.getIsActive() == null || admin.getIsActive();
        String rawRole = (admin.getRole() != null && !admin.getRole().isBlank()) ? admin.getRole().trim().toUpperCase() : "ADMIN";
        String authority = rawRole.startsWith("ROLE_") ? rawRole : "ROLE_" + rawRole;

        return new User(
                admin.getEmail(),
                admin.getPassword(),
                isEnabled,
                true,
                true,
                true,
                Collections.singletonList(new SimpleGrantedAuthority(authority))
        );
    }
}

