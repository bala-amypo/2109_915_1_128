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
        return allocationSnapshotService.getSnapshotById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AllocationSnapshotRecord>> getAll() {
        return ResponseEntity.ok(allocationSnapshotService.getAllSnapshots());
    }
}
