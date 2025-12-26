package com.example.demo.controller;

import com.example.demo.entity.AllocationSnapshotRecord;
import com.example.demo.service.AllocationSnapshotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/snapshots")
public class AllocationSnapshotController {

    private final AllocationSnapshotService allocationSnapshotService;

    public AllocationSnapshotController(AllocationSnapshotService allocationSnapshotService) {
        this.allocationSnapshotService = allocationSnapshotService;
    }

    @PostMapping("/compute/{investorId}")
    public ResponseEntity<AllocationSnapshotRecord> computeSnapshot(@PathVariable Long investorId) {
        return ResponseEntity.ok(allocationSnapshotService.computeSnapshot(investorId));
    }

    @GetMapping("/investor/{investorId}")
    public ResponseEntity<List<AllocationSnapshotRecord>> getByInvestor(@PathVariable Long investorId) {
        return ResponseEntity.ok(allocationSnapshotService.getSnapshotsByInvestor(investorId));
    }

    @GetMapping("/{id}")
public ResponseEntity<AllocationSnapshotRecord> getById(@PathVariable Long id) {
    Optional<AllocationSnapshotRecord> snapshot = allocationSnapshotService.getSnapshotById(id).map(Optional::of);
    return snapshot.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
}


    @GetMapping("/{id}")
public ResponseEntity<RebalancingAlertRecord> getById(@PathVariable Long id) {
    Optional<RebalancingAlertRecord> alert = rebalancingAlertService.getAlertById(id).map(Optional::of);
    return alert.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
}

}
