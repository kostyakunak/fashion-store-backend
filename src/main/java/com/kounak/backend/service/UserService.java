package com.kounak.backend.service;

import com.kounak.backend.model.User;
import com.kounak.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Получить список всех пользователей
    public List<User> getAllUsers() {
        try {
            logger.info("Attempting to fetch all users from repository");
            List<User> users = userRepository.findAll();
            logger.info("Successfully fetched {} users", users.size());
            return users;
        } catch (Exception e) {
            logger.error("Error fetching users: {}", e.getMessage(), e);
            throw e;
        }
    }

    // ✅ Получить пользователя по ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ✅ Проверить существование пользователя
    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }

    // ✅ Проверить, занят ли email
    public boolean isEmailTaken(String email) {
        return userRepository.findAll().stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPhone(user.getPhone());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        
        // Обновляем пароль только если он был передан
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }
        
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}