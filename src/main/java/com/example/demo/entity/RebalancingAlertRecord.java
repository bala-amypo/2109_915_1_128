package com.example.demo.entity;

import com.example.demo.entity.enums.AlertSeverity;
import com.example.demo.entity.enums.AssetClassType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rebalancing_alerts")
public class RebalancingAlertRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "investor_id", nullable = false)
    private Long investorId;
    
    @Column(name = "asset_class", nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetClassType assetClass;
    
    @Column(name = "current_percentage", nullable = false)
    private Double currentPercentage;
    
    @Column(name = "target_percentage", nullable = false)
    private Double targetPercentage;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AlertSeverity severity;
    
    @Column(nullable = false)
    private String message;
    
    @Column(name = "alert_date", nullable = false)
    private LocalDateTime alertDate = LocalDateTime.now();
    
    @Column(nullable = false)
    private boolean resolved = false;

    public RebalancingAlertRecord() {}

    public RebalancingAlertRecord(Long investorId, AssetClassType assetClass, Double currentPercentage, 
                                Double targetPercentage, AlertSeverity severity, String message, 
                                LocalDateTime alertDate, boolean resolved) {
        this.investorId = investorId;
        this.assetClass = assetClass;
        this.currentPercentage = currentPercentage;
        this.targetPercentage = targetPercentage;
        this.severity = severity;
        this.message = message;
        this.alertDate = alertDate;
        this.resolved = resolved;
    }

    // All getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getInvestorId() { return investorId; }
    public void setInvestorId(Long investorId) { this.investorId = investorId; }
    public AssetClassType getAssetClass() { return assetClass; }
    public void setAssetClass(AssetClassType assetClass) { this.assetClass = assetClass; }
    public Double getCurrentPercentage() { return currentPercentage; }
    public void setCurrentPercentage(Double currentPercentage) { this.currentPercentage = currentPercentage; }
    public Double getTargetPercentage() { return targetPercentage; }
    public void setTargetPercentage(Double targetPercentage) { this.targetPercentage = targetPercentage; }
    public AlertSeverity getSeverity() { return severity; }
    public void setSeverity(AlertSeverity severity) { this.severity = severity; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getAlertDate() { return alertDate; }
    public void setAlertDate(LocalDateTime alertDate) { this.alertDate = alertDate; }
    public boolean getResolved() { return resolved; }
    public void setResolved(boolean resolved) { this.resolved = resolved; }
}
