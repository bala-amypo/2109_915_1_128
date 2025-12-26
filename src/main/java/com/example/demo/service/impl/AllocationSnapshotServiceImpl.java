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
    AllocationSnapshotRecordRepository snapshotRecordRepository,     // 0
    HoldingRecordRepository holdingRecordRepository,                 // 1  
    AssetClassAllocationRuleRepository allocationRuleRepository,     // 2
    RebalancingAlertRecordRepository alertRecordRepository           // 3
) { 

        this.snapshotRecordRepository = snapshotRecordRepository;
        this.holdingRecordRepository = holdingRecordRepository;
        this.allocationRuleRepository = allocationRuleRepository;
        this.alertRecordRepository = alertRecordRepository;
    }

    @Override
    public AllocationSnapshotRecord computeSnapshot(Long investorId) {
        List<HoldingRecord> holdings = holdingRecordRepository.findByInvestorId(investorId);
        if (holdings.isEmpty()) {
            throw new IllegalArgumentException("No holdings found for investor");
        }

        double totalValue = holdings.stream().mapToDouble(h -> h.getCurrentValue()).sum();
        if (totalValue <= 0) {
            throw new IllegalArgumentException("Total portfolio value must be > 0");
        }

        Map<AssetClassType, Double> allocationMap = holdings.stream()
                .collect(Collectors.groupingBy(
                    HoldingRecord::getAssetClass,
                    Collectors.summingDouble(h -> h.getCurrentValue())
                ));

        ObjectMapper mapper = new ObjectMapper();
        String allocationJson;
        try {
            allocationJson = mapper.writeValueAsString(allocationMap);
        } catch (Exception e) {
            allocationJson = "{}";
        }

        AllocationSnapshotRecord snapshot = new AllocationSnapshotRecord(
                investorId, LocalDateTime.now(), totalValue, allocationJson
        );
        snapshotRecordRepository.save(snapshot);

        // Check for rebalancing alerts
        List<AssetClassAllocationRule> rules = allocationRuleRepository.findByInvestorIdAndActiveTrue(investorId);
        for (AssetClassAllocationRule rule : rules) {
            AssetClassType assetClass = rule.getAssetClass();
            double currentValue = allocationMap.getOrDefault(assetClass, 0.0);
            double currentPercentage = totalValue > 0 ? (currentValue / totalValue) * 100 : 0;
            
            if (currentPercentage > rule.getTargetPercentage()) {
                RebalancingAlertRecord alert = new RebalancingAlertRecord(
                        investorId, assetClass, currentPercentage, rule.getTargetPercentage(),
                        AlertSeverity.MEDIUM, "Rebalancing needed", LocalDateTime.now(), false
                );
                alertRecordRepository.save(alert);
            }
        }

        return snapshot;
    }

    @Override
    public AllocationSnapshotRecord getSnapshotById(Long id) {
        return snapshotRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Snapshot not found with id: " + id));
    }

    @Override
    public List<AllocationSnapshotRecord> getSnapshotsByInvestor(Long investorId) {
        return snapshotRecordRepository.findByInvestorId(investorId);
    }

    @Override
    public List<AllocationSnapshotRecord> getAllSnapshots() {
        return snapshotRecordRepository.findAll();
    }
}
