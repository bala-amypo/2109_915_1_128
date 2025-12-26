package com.example.demo.controller;

import com.example.demo.entity.RebalancingAlertRecord;
import com.example.demo.service.RebalancingAlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class RebalancingAlertController {

    private final RebalancingAlertService rebalancingAlertService;

    public RebalancingAlertController(RebalancingAlertService rebalancingAlertService) {
        this.rebalancingAlertService = rebalancingAlertService;
    }

    @PostMapping
    public ResponseEntity<RebalancingAlertRecord> create(@RequestBody RebalancingAlertRecord alert) {
        return ResponseEntity.ok(rebalancingAlertService.createAlert(alert));
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<RebalancingAlertRecord> resolve(@PathVariable Long id) {
        return ResponseEntity.ok(rebalancingAlertService.resolveAlert(id));
    }

    @GetMapping("/investor/{investorId}")
    public ResponseEntity<List<RebalancingAlertRecord>> getByInvestor(@PathVariable Long investorId) {
        return ResponseEntity.ok(rebalancingAlertService.getAlertsByInvestor(investorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RebalancingAlertRecord> getById(@PathVariable Long id) {
        return rebalancingAlertService.getAlertById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RebalancingAlertRecord>> getAll() {
        return ResponseEntity.ok(rebalancingAlertService.getAllAlerts());
    }
}
