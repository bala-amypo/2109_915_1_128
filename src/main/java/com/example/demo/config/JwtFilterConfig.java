package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {


    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(
                "thisIsA32ByteMinimumSecureJwtTestKey!",
                3600000L
        );
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider
    ) {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }
}
