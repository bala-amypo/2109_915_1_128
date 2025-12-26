package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.entity.enums.AlertSeverity;
import com.example.demo.entity.enums.AssetClassType;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.AllocationSnapshotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AllocationSnapshotServiceImpl implements AllocationSnapshotService {

    private final AllocationSnapshotRecordRepository snapshotRecordRepository;
private final HoldingRecordRepository holdingRecordRepository;
private final AssetClassAllocationRuleRepository allocationRuleRepository;
private final RebalancingAlertRecordRepository alertRecordRepository;

public AllocationSnapshotServiceImpl(
        AllocationSnapshotRecordRepository snapshotRecordRepository,
        HoldingRecordRepository holdingRecordRepository,
        AssetClassAllocationRuleRepository allocationRuleRepository,
        RebalancingAlertRecordRepository alertRecordRepository) {
    this.snapshotRecordRepository = snapshotRecordRepository;
    this.holdingRecordRepository = holdingRecordRepository;
    this.allocationRuleRepository = allocationRuleRepository;
    this.alertRecordRepository = alertRecordRepository;
}


    @Override
public AllocationSnapshotRecord computeSnapshot(Long investorId) {
    // Fetch holdings
    List<HoldingRecord> holdings = holdingRecordRepository.findByInvestorId(investorId);
    if (holdings.isEmpty()) {
        throw new IllegalArgumentException("No holdings found for investor: " + investorId);
    }
    
    // Calculate total portfolio value
    double totalPortfolioValue = holdings.stream()
            .mapToDouble(HoldingRecord::getCurrentValue)
            .sum();
    
    if (totalPortfolioValue <= 0) {
        throw new IllegalArgumentException("Total portfolio value must be > 0");
    }
    
    // Calculate current percentages per asset class
    Map<AssetClassType, Double> currentAllocations = new HashMap<>();
    for (HoldingRecord holding : holdings) {
        double percentage = (holding.getCurrentValue() / totalPortfolioValue) * 100;
        currentAllocations.put(holding.getAssetClass(), percentage);
    }
    
    // Get active rules
    List<AssetClassAllocationRule> activeRules = allocationRuleRepository.findActiveRulesHql(investorId);
    
    // Create allocation JSON
    ObjectMapper objectMapper = new ObjectMapper();
    String allocationJson;
    try {
        allocationJson = objectMapper.writeValueAsString(currentAllocations);
    } catch (Exception e) {
        allocationJson = "{}";
    }
    
    // Create snapshot
    AllocationSnapshotRecord snapshot = new AllocationSnapshotRecord(
            investorId, 
            LocalDateTime.now(), 
            totalPortfolioValue, 
            allocationJson
    );
    AllocationSnapshotRecord savedSnapshot = snapshotRecordRepository.save(snapshot);
    
    // Create alerts for deviations
    for (AssetClassAllocationRule rule : activeRules) {
        AssetClassType assetClass = rule.getAssetClass();
        Double currentPercentage = currentAllocations.getOrDefault(assetClass, 0.0);
        Double targetPercentage = rule.getTargetPercentage();
        
        if (currentPercentage > targetPercentage) {
            AlertSeverity severity = determineSeverity(currentPercentage - targetPercentage);
            String message = String.format("Asset %s exceeded target: %.1f%% vs %.1f%%", 
                    assetClass, currentPercentage, targetPercentage);
            
            RebalancingAlertRecord alert = new RebalancingAlertRecord(
                    investorId, assetClass, currentPercentage, targetPercentage,
                    severity, message, LocalDateTime.now(), false
            );
            alertRecordRepository.save(alert);
        }
    }
    
    return savedSnapshot;
}

private AlertSeverity determineSeverity(double deviation) {
    if (deviation > 20) return AlertSeverity.HIGH;
    if (deviation > 10) return AlertSeverity.MEDIUM;
    return AlertSeverity.LOW;
}

