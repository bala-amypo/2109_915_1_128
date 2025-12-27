package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.example.demo.entity.enums.AssetClassType;

@Entity
public class HoldingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long investorId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetClassType assetClass;

    @Column(nullable = false)
    private Double currentValue;

    @Column(nullable = false)
    private LocalDateTime snapshotDate;

    public HoldingRecord() {}

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

    public AssetClassType getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(AssetClassType assetClass) {
        this.assetClass = assetClass;
    }

  public Double getCurrentValue() { return currentValue; }

    public void setValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public LocalDateTime getSnapshotDate() {
        return snapshotDate;
    }
}
