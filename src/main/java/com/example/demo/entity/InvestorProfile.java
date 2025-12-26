package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class InvestorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String investorId;
    private String fullName;
    private String email;
    private Boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    public InvestorProfile() {}

    public InvestorProfile(String investorId, String fullName,
                           String email, Boolean active) {
        this.investorId = investorId;
        this.fullName = fullName;
        this.email = email;
        this.active = active;
    }

    public Long getId() { return id; }
    public String getInvestorId() { return investorId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public Boolean getActive() { return active; }

    public void setId(Long id) { this.id = id; }
    public void setActive(Boolean active) { this.active = active; }
}
