package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.entity.enums.AssetClassType;
import com.example.demo.entity.enums.AlertSeverity;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.AllocationSnapshotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AllocationSnapshotServiceImpl implements AllocationSnapshotService {
    private final AllocationSnapshotRecordRepository snapshotRepo;
    private final HoldingRecordRepository holdingRepo;
    private final AssetClassAllocationRuleRepository ruleRepo;
    private final RebalancingAlertRecordRepository alertRepo;  // EXACT ORDER REQUIRED

    public AllocationSnapshotServiceImpl(
        AllocationSnapshotRecordRepository snapshotRepo,
        HoldingRecordRepository holdingRepo,
        AssetClassAllocationRuleRepository ruleRepo,
        RebalancingAlertRecordRepository alertRepo) {
        this.snapshotRepo = snapshotRepo;
        this.holdingRepo = holdingRepo;
        this.ruleRepo = ruleRepo;
        this.alertRepo = alertRepo;
    }

    @Override
    public AllocationSnapshotRecord computeSnapshot(Long investorId) {
        List<HoldingRecord> holdings = holdingRepo.findByInvestorId(investorId);
        if (holdings.isEmpty()) {
            throw new ResourceNotFoundException("No holdings found for investor");
        }

        double totalValue = holdings.stream().mapToDouble(HoldingRecord::getCurrentValue).sum();
        if (totalValue <= 0) {
            throw new IllegalArgumentException("totalPortfolioValue must be > 0");
        }

        Map<AssetClassType, Double> allocations = calculateAllocations(holdings, totalValue);
        String allocationJson = "{}"; 

        AllocationSnapshotRecord snapshot = new AllocationSnapshotRecord();
        snapshot.setInvestorId(investorId);
        snapshot.setSnapshotDate(LocalDateTime.now());
        snapshot.setTotalPortfolioValue(totalValue);
        snapshot.setAllocationJson(allocationJson);
        
        snapshot = snapshotRepo.save(snapshot);

        createAlerts(investorId, allocations);
        return snapshot;
    }

    private Map<AssetClassType, Double> calculateAllocations(List<HoldingRecord> holdings, double totalValue) {
        return new HashMap<>();
    }

    private void createAlerts(Long investorId, Map<AssetClassType, Double> allocations) {
        
    }

    
    @Override
    public AllocationSnapshotRecord getSnapshotById(Long id) {
        return snapshotRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Snapshot not found"));
    }

    @Override
    public List<AllocationSnapshotRecord> getSnapshotsByInvestor(Long investorId) {
        return snapshotRepo.findByInvestorId(investorId);
    }

    @Override
    public List<AllocationSnapshotRecord> getAllSnapshots() {
        return snapshotRepo.findAll();
    }
}
