package com.kounak.backend.controller;

import com.kounak.backend.model.User;
import com.kounak.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();
        System.out.println("Users from DB: " + users); // ЛОГ ДЛЯ ПРОВЕРКИ
        return users;
    }
}