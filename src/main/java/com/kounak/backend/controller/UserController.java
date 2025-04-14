package com.kounak.backend.controller;

import com.kounak.backend.model.User;
import com.kounak.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            // Проверяем, что пользователь существует
            if (!userService.userExists(id)) {
                logger.error("Пользователь с ID {} не найден", id);
                return ResponseEntity.notFound().build();
            }
            
            // Проверяем, что email уникален (если он изменился)
            User existingUser = userService.getUserById(id);
            if (!existingUser.getEmail().equals(user.getEmail()) && 
                userService.isEmailTaken(user.getEmail())) {
                logger.error("Email {} уже используется другим пользователем", user.getEmail());
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            User updatedUser = userService.updateUser(id, user);
            logger.info("Обновлен пользователь с ID {}", id);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            logger.error("Пользователь с ID {} не найден", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Ошибка при обновлении пользователя {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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