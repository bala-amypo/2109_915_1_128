package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "investorId"),
        @UniqueConstraint(columnNames = "email")
    }
)
public class InvestorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String investorId;
    private String fullName;
    private String email;
    private Boolean active = true;
    private LocalDateTime createdAt = LocalDateTime.now();

    // getters & setters
}
