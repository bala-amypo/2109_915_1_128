package com.example.demo.service.impl;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserAccount;
import com.example.demo.entity.enums.RoleType;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserAccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserAccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserAccount registerUser(RegisterRequest request) {
        UserAccount user = new UserAccount();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(RoleType.valueOf(request.getRole() != null ? request.getRole() : "INVESTOR"));
        return repository.save(user);
    }

    @Override
    public UserAccount findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }
}
