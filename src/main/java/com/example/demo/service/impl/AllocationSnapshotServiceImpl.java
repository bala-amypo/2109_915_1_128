package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.entity.enums.AlertSeverity;
import com.example.demo.repository.*;
import com.example.demo.service.AllocationSnapshotService;

import java.time.LocalDateTime;
import java.util.*;

public class AllocationSnapshotServiceImpl implements AllocationSnapshotService {

    private final AllocationSnapshotRecordRepository snapshotRepo;
    private final HoldingRecordRepository holdingRepo;
    private final AssetClassAllocationRuleRepository ruleRepo;
    private final RebalancingAlertRecordRepository alertRepo;

    public AllocationSnapshotServiceImpl(
            AllocationSnapshotRecordRepository snapshotRepo,
            HoldingRecordRepository holdingRepo,
            AssetClassAllocationRuleRepository ruleRepo,
            RebalancingAlertRecordRepository alertRepo
    ) {
        this.snapshotRepo = snapshotRepo;
        this.holdingRepo = holdingRepo;
        this.ruleRepo = ruleRepo;
        this.alertRepo = alertRepo;
    }

    @Override
    public AllocationSnapshotRecord computeSnapshot(Long investorId) {

        List<HoldingRecord> holdings = holdingRepo.findByInvestorId(investorId);
        if (holdings.isEmpty()) {
            throw new IllegalArgumentException("No holdings found");
        }

        double total = holdings.stream()
                .mapToDouble(HoldingRecord::getCurrentValue)
                .sum();

        Map<String, Double> allocationMap = new HashMap<>();
        for (HoldingRecord h : holdings) {
            allocationMap.merge(
                    h.getAssetClass().name(),
                    h.getCurrentValue(),
                    Double::sum
            );
        }

        AllocationSnapshotRecord snapshot =
                new AllocationSnapshotRecord(
                        investorId,
                        LocalDateTime.now(),
                        total,
                        allocationMap.toString()
                );

        snapshot = snapshotRepo.save(snapshot);

        List<AssetClassAllocationRule> rules =
                ruleRepo.findByInvestorIdAndActiveTrue(investorId);

        for (AssetClassAllocationRule rule : rules) {
            double currentValue =
                    allocationMap.getOrDefault(rule.getAssetClass().name(), 0.0);

            double currentPercentage = (currentValue / total) * 100;

            if (currentPercentage > rule.getTargetPercentage()) {
                RebalancingAlertRecord alert =
                        new RebalancingAlertRecord(
                                investorId,
                                rule.getAssetClass(),
                                currentPercentage,
                                rule.getTargetPercentage(),
                                AlertSeverity.MEDIUM,
                                "Rebalance needed",
                                LocalDateTime.now(),
                                false
                        );
                alertRepo.save(alert);
            }
        }
        return snapshot;
    }

    @Override
    public AllocationSnapshotRecord getSnapshotById(Long id) {
        return snapshotRepo.findById(id)
                .orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException(
                        "Snapshot not found with id " + id));
    }

    @Override
    public List<AllocationSnapshotRecord> getAllSnapshots() {
        return snapshotRepo.findAll();
    }
}
