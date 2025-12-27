package com.example.demo.entity;
import jakarta.persistence.*;
import com.example.demo.entity.enums.AssetClassType;

@Entity
public class AssetClassAllocationRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long investorId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetClassType assetClass;

    @Column(nullable = false)
    private Double targetPercentage;

    @Column(nullable = false)
    private Boolean active = true;

    public AssetClassAllocationRule() {}
    public AssetClassAllocationRule(
        Long investorId,
        AssetClassType assetClass,
        double targetPercentage,
        boolean active
) {
    this.investorId = investorId;
    this.assetClass = assetClass;
    this.targetPercentage = targetPercentage;
    this.active = active;
}


    // Getters and setters
    public void setId(Long id) {
    this.id = id;
}


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

    public Double getTargetPercentage() {
        return targetPercentage;
    }

    public void setTargetPercentage(Double targetPercentage) {
        this.targetPercentage = targetPercentage;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
