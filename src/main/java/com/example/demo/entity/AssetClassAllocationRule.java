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

    public AssetClassAllocationRule(Long investorId, AssetClassType assetClass,
                                    Double targetPercentage, Boolean active) {
        if (targetPercentage < 0 || targetPercentage > 100) {
            throw new IllegalArgumentException("targetPercentage must be between 0 and 100");
        }
        this.investorId = investorId;
        this.assetClass = assetClass;
        this.targetPercentage = targetPercentage;
        this.active = active;
    }

    // getters and setters
}
