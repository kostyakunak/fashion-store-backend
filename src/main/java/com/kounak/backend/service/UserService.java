package com.kounak.backend.service;

import com.kounak.backend.model.User;
import com.kounak.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Получить список всех пользователей
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Получить пользователя по ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}