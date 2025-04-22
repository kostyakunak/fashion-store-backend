package com.kounak.backend.service;

import com.kounak.backend.dto.LoginRequest;
import com.kounak.backend.dto.RegisterRequest;
import com.kounak.backend.dto.UserResponse;
import com.kounak.backend.exception.AuthException;
import com.kounak.backend.model.User;
import com.kounak.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException("Email уже используется");
        }

        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        return convertToUserResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException("Неверный email или пароль"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()) || !user.isEnabled()) {
            throw new AuthException("Неверный email или пароль");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return convertToUserResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        return convertToUserResponse(user);
    }

    private UserResponse convertToUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
} 