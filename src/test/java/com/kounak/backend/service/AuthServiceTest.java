package com.kounak.backend.service;

import com.kounak.backend.dto.LoginRequest;
import com.kounak.backend.dto.RegisterRequest;
import com.kounak.backend.dto.UserResponse;
import com.kounak.backend.exception.AuthException;
import com.kounak.backend.model.User;
import com.kounak.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private User savedUser;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john@example.com");
        registerRequest.setPhone("+79123456789");
        registerRequest.setPassword("password123");

        savedUser = new User();
        savedUser.setId(1L);
        savedUser.setFirstName("John");
        savedUser.setLastName("Doe");
        savedUser.setEmail("john@example.com");
        savedUser.setPhone("+79123456789");
        savedUser.setPassword("hashedPassword");
        savedUser.setRole("USER");
        savedUser.setEnabled(true);
        savedUser.setCreatedAt(LocalDateTime.now());

        loginRequest = new LoginRequest();
        loginRequest.setEmail("john@example.com");
        loginRequest.setPassword("password123");
    }

    @Test
    void register_ValidRequest_ReturnsUserResponse() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("john@example.com", response.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_ExistingEmail_ThrowsException() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        AuthException exception = assertThrows(AuthException.class, 
            () -> authService.register(registerRequest));
        assertEquals("Email уже используется", exception.getMessage());
        
        verify(userRepository).existsByEmail(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void login_ValidCredentials_ReturnsUserResponse() {
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(savedUser));
        when(passwordEncoder.matches(loginRequest.getPassword(), savedUser.getPassword())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("john@example.com", response.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_InvalidPassword_ThrowsException() {
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(savedUser));
        when(passwordEncoder.matches(loginRequest.getPassword(), savedUser.getPassword())).thenReturn(false);

        AuthException exception = assertThrows(AuthException.class, 
            () -> authService.login(loginRequest));
        assertEquals("Неверный email или пароль", exception.getMessage());
    }

    @Test
    void login_UserNotFound_ThrowsException() {
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        AuthException exception = assertThrows(AuthException.class, 
            () -> authService.login(loginRequest));
        assertEquals("Неверный email или пароль", exception.getMessage());
    }
} 