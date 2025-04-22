package com.kounak.backend.controller;

import com.kounak.backend.model.User;
import com.kounak.backend.repository.UserRepository;
import com.kounak.backend.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String email = loginRequest.get("email");
            String password = loginRequest.get("password");
            
            // Аутентификация пользователя
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // Сохранение аутентификации в контексте безопасности
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Получение данных пользователя
            Optional<User> userOpt = userRepository.findByEmail(email);
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                
                // Создаем UserDetails для генерации токена
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                );
                
                // Генерируем JWT токен
                String token = jwtTokenUtil.generateToken(userDetails);
                
                // Обновляем время последнего входа
                user.setLastLogin(LocalDateTime.now());
                userRepository.save(user);
                
                // Создание ответа с информацией о пользователе и токеном
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("email", user.getEmail());
                response.put("firstName", user.getFirstName());
                response.put("lastName", user.getLastName());
                response.put("role", user.getRole());
                
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Ошибка при получении данных пользователя");
            }
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Неверный email или пароль");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> registerRequest) {
        try {
            // Проверяем, существует ли пользователь с таким email
            if (userRepository.existsByEmail(registerRequest.get("email"))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Email уже используется");
            }
            
            // Создаем нового пользователя
            User user = new User();
            user.setFirstName(registerRequest.get("firstName"));
            user.setLastName(registerRequest.get("lastName"));
            user.setEmail(registerRequest.get("email"));
            user.setPhone(registerRequest.get("phone"));
            user.setPassword(passwordEncoder.encode(registerRequest.get("password")));
            user.setRole("USER");
            user.setEnabled(true);
            user.setCreatedAt(LocalDateTime.now());
            
            // Сохраняем пользователя
            User savedUser = userRepository.save(user);
            
            // Создаем UserDetails для генерации токена
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                savedUser.getEmail(),
                savedUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + savedUser.getRole()))
            );
            
            // Генерируем JWT токен
            String token = jwtTokenUtil.generateToken(userDetails);
            
            // Создаем ответ с информацией о пользователе и токеном
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("email", savedUser.getEmail());
            response.put("firstName", savedUser.getFirstName());
            response.put("lastName", savedUser.getLastName());
            response.put("role", savedUser.getRole());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при регистрации: " + e.getMessage());
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Выход выполнен успешно");
    }
} 