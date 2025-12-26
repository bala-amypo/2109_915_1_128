package com.example.demo.entity;

import com.example.demo.entity.enums.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RebalancingAlertRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long investorId;

    @Enumerated(EnumType.STRING)
    private AssetClassType assetClass;

    private Double currentPercentage;
    private Double targetPercentage;

    @Enumerated(EnumType.STRING)
    private AlertSeverity severity;

    private String message;
    private LocalDateTime alertDate;
    private Boolean resolved = false;

    public RebalancingAlertRecord() {}

    public RebalancingAlertRecord(Long investorId,
                                  AssetClassType assetClass,
                                  Double currentPercentage,
                                  Double targetPercentage,
                                  AlertSeverity severity,
                                  String message,
                                  LocalDateTime alertDate,
                                  Boolean resolved) {
        if (currentPercentage <= targetPercentage) {
            throw new IllegalArgumentException("currentPercentage > targetPercentage");
        }
        this.investorId = investorId;
        this.assetClass = assetClass;
        this.currentPercentage = currentPercentage;
        this.targetPercentage = targetPercentage;
        this.severity = severity;
        this.message = message;
        this.alertDate = alertDate;
        this.resolved = resolved;
    }

    public Double getCurrentPercentage() { return currentPercentage; }
    public Double getTargetPercentage() { return targetPercentage; }
    public Boolean getResolved() { return resolved; }

    public void setResolved(Boolean resolved) { this.resolved = resolved; }
    public void setId(Long id) { this.id = id; }
}
