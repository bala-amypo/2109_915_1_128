package com.example.demo.service;

import com.example.demo.entity.UserAccount;
import java.util.Optional;

public interface UserAccountService {
    UserAccount register(com.example.demo.dto.RegisterRequest request);
    Optional<UserAccount> findByEmail(String email);
}
