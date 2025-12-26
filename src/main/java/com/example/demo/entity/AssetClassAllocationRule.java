package com.example.demo.entity;

import com.example.demo.entity.enums.AssetClassType;
import jakarta.persistence.*;

@Entity
public class AssetClassAllocationRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long investorId;

    @Enumerated(EnumType.STRING)
    private AssetClassType assetClass;

    private Double targetPercentage;
    private Boolean active;

    public AssetClassAllocationRule() {}

    public AssetClassAllocationRule(Long investorId,
                                    AssetClassType assetClass,
                                    Double targetPercentage,
                                    Boolean active) {
        if (targetPercentage < 0 || targetPercentage > 100) {
            throw new IllegalArgumentException("between 0 and 100");
        }
        this.investorId = investorId;
        this.assetClass = assetClass;
        this.targetPercentage = targetPercentage;
        this.active = active;
    }

    public Long getInvestorId() { return investorId; }
    public AssetClassType getAssetClass() { return assetClass; }
    public Double getTargetPercentage() { return targetPercentage; }
    public Boolean getActive() { return active; }

    public void setId(Long id) { this.id = id; }
}
