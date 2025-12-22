package com.example.demo.controller;

import com.example.demo.entity.AssetClassAllocationRule;
import com.example.demo.service.AllocationRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/allocation-rules")
public class AllocationRuleController {
    private final AllocationRuleService service;

    public AllocationRuleController(AllocationRuleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AssetClassAllocationRule> create(@RequestBody AssetClassAllocationRule rule) {
        return ResponseEntity.ok(service.createRule(rule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetClassAllocationRule> update(@PathVariable Long id, @RequestBody AssetClassAllocationRule rule) {
        return ResponseEntity.ok(service.updateRule(id, rule));
    }

    @GetMapping("/investor/{investorId}")
    public ResponseEntity<List<AssetClassAllocationRule>> getByInvestor(@PathVariable Long investorId) {
        return ResponseEntity.ok(service.getRulesByInvestor(investorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetClassAllocationRule> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRuleById(id));
    }

    @GetMapping
    public ResponseEntity<List<AssetClassAllocationRule>> getAll() {
        return ResponseEntity.ok(service.getActiveRules(1L)); 
    }
}
