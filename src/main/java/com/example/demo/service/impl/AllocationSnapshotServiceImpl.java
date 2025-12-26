package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.entity.enums.AlertSeverity;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.AllocationSnapshotService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AllocationSnapshotServiceImpl implements AllocationSnapshotService {

    private final AllocationSnapshotRecordRepository snapshotRepo;
    private final HoldingRecordRepository holdingRepo;
    private final AssetClassAllocationRuleRepository ruleRepo;
    private final RebalancingAlertRecordRepository alertRepo;

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
            throw new IllegalArgumentException("No holdings");
        }

        double total = holdings.stream()
                .mapToDouble(HoldingRecord::getCurrentValue)
                .sum();

        AllocationSnapshotRecord snapshot =
                new AllocationSnapshotRecord(
                        investorId,
                        LocalDateTime.now(),
                        total,
                        "{}"
                );

        snapshotRepo.save(snapshot);

        for (AssetClassAllocationRule rule :
                ruleRepo.findByInvestorIdAndActiveTrue(investorId)) {

            double classTotal = holdings.stream()
                    .filter(h -> h.getAssetClass() == rule.getAssetClass())
                    .mapToDouble(HoldingRecord::getCurrentValue)
                    .sum();

            double pct = (classTotal / total) * 100;

            if (pct > rule.getTargetPercentage()) {
                alertRepo.save(new RebalancingAlertRecord(
                        investorId,
                        rule.getAssetClass(),
                        pct,
                        rule.getTargetPercentage(),
                        AlertSeverity.HIGH,
                        "Rebalance required",
                        LocalDateTime.now(),
                        false
                ));
            }
        }

        return snapshot;
    }

    @Override
    public AllocationSnapshotRecord getSnapshotById(Long id) {
        return snapshotRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Snapshot not found " + id));
    }

    @Override
    public List<AllocationSnapshotRecord> getAllSnapshots() {
        return snapshotRepo.findAll();
    }
}
