package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AllocationSnapshotRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long investorId;

    @Column(nullable = false)
    private LocalDateTime snapshotDate;

    @Column(nullable = false)
    private Double totalPortfolioValue;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String allocationJson;
    new AllocationSnapshotRecord(
    investorId,
    LocalDateTime.now(),
    totalValue,
    allocationJson
);

    public AllocationSnapshotRecord() {}
     public AllocationSnapshotRecord(investorId,LocalDateTime.now(),totalValue,allocationJson) {}

    @PrePersist
    public void onCreate() {
        this.snapshotDate = LocalDateTime.now();
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public Long getInvestorId() {
        return investorId;
    }

    public void setInvestorId(Long investorId) {
        this.investorId = investorId;
    }

    public LocalDateTime getSnapshotDate() {
        return snapshotDate;
    }

    public Double getTotalPortfolioValue() {
        return totalPortfolioValue;
    }

    public void setTotalPortfolioValue(Double totalPortfolioValue) {
        this.totalPortfolioValue = totalPortfolioValue;
    }

    public String getAllocationJson() {
        return allocationJson;
    }

    public void setAllocationJson(String allocationJson) {
        this.allocationJson = allocationJson;
    }
  
}
