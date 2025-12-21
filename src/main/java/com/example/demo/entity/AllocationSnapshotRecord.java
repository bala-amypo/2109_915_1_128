package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "allocation_snapshot_records")
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

    @Lob
    @Column(nullable = false)
    private String allocationJson;

    public void setTotalPortfolioValue(Double totalPortfolioValue) {
        if (totalPortfolioValue == null || totalPortfolioValue <= 0.0) {
            throw new IllegalArgumentException("totalPortfolioValue must be > 0");
        }
        this.totalPortfolioValue = totalPortfolioValue;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getInvestorId() { return investorId; }
    public void setInvestorId(Long investorId) { this.investorId = investorId; }

    public LocalDateTime getSnapshotDate() { return snapshotDate; }
    public void setSnapshotDate(LocalDateTime snapshotDate) { this.snapshotDate = snapshotDate; }

    public Double getTotalPortfolioValue() { return totalPortfolioValue; }

    public String getAllocationJson() { return allocationJson; }
    public void setAllocationJson(String allocationJson) { this.allocationJson = allocationJson; }
}
