package com.kounak.backend.controller;

import com.kounak.backend.dto.LoginRequest;
import com.kounak.backend.dto.RegisterRequest;
import com.kounak.backend.dto.UserResponse;
import com.kounak.backend.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;
import com.kounak.backend.config.TestSecurityConfig;
import com.kounak.backend.config.TestJpaConfig;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import({TestSecurityConfig.class, TestJpaConfig.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void register_ValidRequest_ReturnsOk() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        request.setPhone("+79123456789");
        request.setPassword("password123");

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setFirstName("John");
        response.setLastName("Doe");
        response.setEmail("john@example.com");
        response.setPhone("+79123456789");
        response.setRole("USER");
        response.setCreatedAt(LocalDateTime.now());

        when(authService.register(any(RegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void login_ValidCredentials_ReturnsOk() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("john@example.com");
        request.setPassword("password123");

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setFirstName("John");
        response.setLastName("Doe");
        response.setEmail("john@example.com");
        response.setPhone("+79123456789");
        response.setRole("USER");
        response.setLastLogin(LocalDateTime.now());

        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    @WithMockUser(username = "john@example.com")
    void getCurrentUser_Authenticated_ReturnsOk() throws Exception {
        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setFirstName("John");
        response.setLastName("Doe");
        response.setEmail("john@example.com");
        response.setPhone("+79123456789");
        response.setRole("USER");

        when(authService.getCurrentUser("john@example.com")).thenReturn(response);

        mockMvc.perform(get("/api/auth/current-user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
} 