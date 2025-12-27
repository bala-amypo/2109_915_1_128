package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager,
                          UserAccountRepository userAccountRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserAccount user = userAccountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenProvider.generateToken(authentication, user);

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());

        return ResponseEntity.ok(response);
    }
}
