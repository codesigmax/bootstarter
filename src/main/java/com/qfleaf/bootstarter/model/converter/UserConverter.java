package com.qfleaf.bootstarter.model.converter;

import com.qfleaf.bootstarter.model.User;
import com.qfleaf.bootstarter.model.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final PasswordEncoder passwordEncoder;

    public User toEntity(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
    }
}
