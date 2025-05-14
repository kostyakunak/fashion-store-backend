package com.kounak.backend.controller;

import com.kounak.backend.model.User;
import com.kounak.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            logger.info("Получено {} пользователей", users.size());
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Ошибка при получении пользователей: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            logger.info("Получен пользователь с ID: {}", id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            logger.error("Пользователь с ID {} не найден", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Ошибка при получении пользователя {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            // Проверяем, что обязательные поля заполнены
            if (user.getEmail() == null || user.getFirstName() == null || 
                user.getLastName() == null || user.getPhone() == null || 
                user.getPassword() == null || user.getRole() == null) {
                logger.error("Не все обязательные поля заполнены");
                return ResponseEntity.badRequest().build();
            }
            
            // Проверяем, что email уникален
            if (userService.isEmailTaken(user.getEmail())) {
                logger.error("Email {} уже используется", user.getEmail());
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            // Сбрасываем ID для автогенерации
            user.setId(null);
            
            User savedUser = userService.createUser(user);
            logger.info("Создан новый пользователь с ID {}", savedUser.getId());
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            logger.error("Ошибка при создании пользователя: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            // Проверяем, что пользователь существует
            if (!userService.userExists(id)) {
                logger.error("Пользователь с ID {} не найден", id);
                return ResponseEntity.notFound().build();
            }
            
            // Получаем существующего пользователя
            User existingUser = userService.getUserById(id);
            
            // Проверяем, что email уникален (если он изменился)
            if (user.getEmail() != null && !existingUser.getEmail().equals(user.getEmail()) && 
                userService.isEmailTaken(user.getEmail())) {
                logger.error("Email {} уже используется другим пользователем", user.getEmail());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "success", false,
                    "message", "Email уже используется другим пользователем"
                ));
            }
            
            // Валидация данных перед обновлением
            Map<String, String> validationErrors = validateUserData(user);
            if (!validationErrors.isEmpty()) {
                logger.error("Ошибка валидации данных пользователя: {}", validationErrors);
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Ошибка валидации данных",
                    "errors", validationErrors
                ));
            }
            
            // Обновляем только переданные поля, оставляем существующие значения, если поле не передано
            if (user.getFirstName() != null) existingUser.setFirstName(user.getFirstName());
            if (user.getLastName() != null) existingUser.setLastName(user.getLastName());
            if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
            if (user.getPhone() != null) existingUser.setPhone(user.getPhone());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) existingUser.setPassword(user.getPassword());
            if (user.getRole() != null) existingUser.setRole(user.getRole());
            
            // Обновляем адрес, если он передан
            if (user.getAddress() != null) {
                existingUser.setAddress(user.getAddress());
            }
            
            // Обновляем статус аккаунта (enabled/disabled)
            existingUser.setEnabled(user.isEnabled());
            
            User updatedUser = userService.updateUser(id, existingUser);
            logger.info("Обновлен пользователь с ID {}", id);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            logger.error("Пользователь с ID {} не найден", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Ошибка при обновлении пользователя {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "success", false,
                "message", "Ошибка сервера при обновлении пользователя: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Валидация данных пользователя
     */
    private Map<String, String> validateUserData(User user) {
        Map<String, String> errors = new HashMap<>();
        
        // Валидация телефона
        if (user.getPhone() != null && !user.getPhone().isEmpty()) {
            if (!user.getPhone().matches("^\\+7\\d{10}$") && !user.getPhone().matches("^\\d{10,11}$")) {
                errors.put("phone", "Неверный формат телефона. Используйте формат +7XXXXXXXXXX или 10-11 цифр");
            }
        }
        
        // Валидация email
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            if (!user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                errors.put("email", "Неверный формат email");
            }
        }
        
        // Валидация адреса
        if (user.getAddress() != null) {
            if (user.getAddress().length() > 255) {
                errors.put("address", "Адрес слишком длинный (максимум 255 символов)");
            }
        }
        
        return errors;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            // Проверяем, что пользователь существует
            if (!userService.userExists(id)) {
                logger.error("Пользователь с ID {} не найден", id);
                return ResponseEntity.notFound().build();
            }
            
            userService.deleteUser(id);
            logger.info("Удален пользователь с ID {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Ошибка при удалении пользователя {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}