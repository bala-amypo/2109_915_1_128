package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.entity.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserAccountRepository userRepo;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            UserAccountRepository userRepo
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepo = userRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String email,
            @RequestParam String password
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        UserAccount user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenProvider.generateToken(
                authentication.getName(),
                user.getId()
        );

        return ResponseEntity.ok(Map.of("token", token));
    }
}
