package com.example.demo.entity;

import com.example.demo.entity.enums.AssetClassType;
import jakarta.persistence.*;

@Entity
@Table(name = "allocation_rules")
public class AssetClassAllocationRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "investor_id", nullable = false)
    private Long investorId;
    
    @Column(name = "asset_class", nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetClassType assetClass;
    
    @Column(name = "target_percentage", nullable = false)
    private Double targetPercentage;
    
    @Column(nullable = false)
    private boolean active = true;

    public AssetClassAllocationRule() {}

    public AssetClassAllocationRule(Long investorId, AssetClassType assetClass, Double targetPercentage, boolean active) {
        this.investorId = investorId;
        this.assetClass = assetClass;
        this.targetPercentage = targetPercentage;
        this.active = active;
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getInvestorId() { return investorId; }
    public void setInvestorId(Long investorId) { this.investorId = investorId; }
    public AssetClassType getAssetClass() { return assetClass; }
    public void setAssetClass(AssetClassType assetClass) { this.assetClass = assetClass; }
    public Double getTargetPercentage() { return targetPercentage; }
    public void setTargetPercentage(Double targetPercentage) { this.targetPercentage = targetPercentage; }
    public boolean getActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
