package com.example.demo.service;

import com.example.demo.entity.UserAccount;

public interface UserService {
    UserAccount registerUser(com.example.demo.dto.RegisterRequest request);
    UserAccount findByEmail(String email);
}
