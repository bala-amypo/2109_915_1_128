package com.example.demo.entity;

import com.example.demo.entity.enums.AssetClass;
import jakarta.persistence.*;

@Entity
public class HoldingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long investorId;

    @Enumerated(EnumType.STRING)
    private AssetClass assetClass;

    private double currentValue;

    // ===== Constructors =====
    public HoldingRecord() {
    }

    public HoldingRecord(Long investorId, AssetClass assetClass, double currentValue) {
        this.investorId = investorId;
        this.assetClass = assetClass;
        this.currentValue = currentValue;
    }

    // ===== Getters & Setters =====
    public Long getId() {
        return id;
    }

    public Long getInvestorId() {
        return investorId;
    }

    public void setInvestorId(Long investorId) {
        this.investorId = investorId;
    }

    public AssetClass getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(AssetClass assetClass) {
        this.assetClass = assetClass;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }
}
