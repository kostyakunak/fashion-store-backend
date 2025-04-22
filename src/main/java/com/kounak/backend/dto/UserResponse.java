package com.kounak.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
} 