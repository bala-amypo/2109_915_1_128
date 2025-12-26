package com.example.demo.controller;

import com.example.demo.entity.AssetClassAllocationRule;
import com.example.demo.service.AllocationRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allocation-rules")
public class AllocationRuleController {

    private final AllocationRuleService allocationRuleService;

    public AllocationRuleController(AllocationRuleService allocationRuleService) {
        this.allocationRuleService = allocationRuleService;
    }

    @PostMapping
    public ResponseEntity<AssetClassAllocationRule> create(@RequestBody AssetClassAllocationRule rule) {
        return ResponseEntity.ok(allocationRuleService.createRule(rule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetClassAllocationRule> update(@PathVariable Long id, @RequestBody AssetClassAllocationRule rule) {
        return ResponseEntity.ok(allocationRuleService.updateRule(id, rule));
    }

    @GetMapping("/investor/{investorId}")
    public ResponseEntity<List<AssetClassAllocationRule>> getByInvestor(@PathVariable Long investorId) {
        return ResponseEntity.ok(allocationRuleService.getRulesByInvestor(investorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetClassAllocationRule> getById(@PathVariable Long id) {
        return ResponseEntity.ok(allocationRuleService.getRuleById(id));
    }

    @GetMapping
    public ResponseEntity<List<AssetClassAllocationRule>> getAll() {
        return ResponseEntity.ok(allocationRuleService.getAllRules());
    }
}
