package com.example.demo.entity;

import com.example.demo.entity.enums.AssetClassType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class HoldingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long investorId;

    @Enumerated(EnumType.STRING)
    private AssetClassType assetClass;

    private Double currentValue;
    private LocalDateTime snapshotDate;

    public HoldingRecord() {}

    public HoldingRecord(Long investorId, AssetClassType assetClass,
                         Double currentValue, LocalDateTime snapshotDate) {
        if (currentValue <= 0) {
            throw new IllegalArgumentException("currentValue must be > 0");
        }
        this.investorId = investorId;
        this.assetClass = assetClass;
        this.currentValue = currentValue;
        this.snapshotDate = snapshotDate;
    }

    // getters and setters
}
