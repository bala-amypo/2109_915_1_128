package com.example.demo.config;

import com.example.demo.entity.UserAccount;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long validityInMillis;


    public JwtTokenProvider(String secret, long validityInMillis) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityInMillis = validityInMillis;
    }

    
    public JwtTokenProvider() {
        this.secretKey = Keys.hmacShaKeyFor(
                "defaultDefaultDefaultDefaultKey!".getBytes());
        this.validityInMillis = 3600000L;
    }

    
    public String generateToken(Authentication authentication, UserAccount user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMillis);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
