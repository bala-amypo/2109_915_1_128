package com.example.demo.entity;

import com.example.demo.entity.enums.AssetClassType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "holding_records")
public class HoldingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "investor_id", nullable = false)
    private Long investorId;
    
    @Column(name = "asset_class", nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetClassType assetClass;
    
    @Column(name = "current_value", nullable = false)
    private Double currentValue;
    
    @Column(name = "snapshot_date")
    private LocalDateTime snapshotDate = LocalDateTime.now();

    // Default constructor - REQUIRED for JPA
    public HoldingRecord() {}

    // Constructor matching test usage - 3 parameters (used in test case priority 14)
    public HoldingRecord(Long investorId, AssetClassType assetClass, double currentValue) {
        this.investorId = investorId;
        this.assetClass = assetClass;
        this.currentValue = currentValue;
        this.snapshotDate = LocalDateTime.now();
    }

    // 4-parameter constructor for flexibility
    public HoldingRecord(Long investorId, AssetClassType assetClass, Double currentValue, LocalDateTime snapshotDate) {
        this.investorId = investorId;
        this.assetClass = assetClass;
        this.currentValue = currentValue;
        this.snapshotDate = snapshotDate;
    }

    // ALL getters and setters - REQUIRED for JPA and tests
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getInvestorId() { return investorId; }
    public void setInvestorId(Long investorId) { this.investorId = investorId; }
    
    public AssetClassType getAssetClass() { return assetClass; }
    public void setAssetClass(AssetClassType assetClass) { this.assetClass = assetClass; }
    
    // CONSISTENT Double return type to match field
    public Double getCurrentValue() { return currentValue; }
    public void setCurrentValue(Double currentValue) { this.currentValue = currentValue; }
    
    public LocalDateTime getSnapshotDate() { return snapshotDate; }
    public void setSnapshotDate(LocalDateTime snapshotDate) { this.snapshotDate = snapshotDate; }
}
