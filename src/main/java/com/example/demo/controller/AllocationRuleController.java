package com.example.demo.controller;

import com.example.demo.entity.AssetClassAllocationRule;
import com.example.demo.service.AllocationRuleService;
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
    public AssetClassAllocationRule create(@RequestBody AssetClassAllocationRule rule) {
        return allocationRuleService.createRule(rule);
    }

    @PutMapping("/{id}")
    public AssetClassAllocationRule update(
            @PathVariable Long id,
            @RequestBody AssetClassAllocationRule rule) {
        return allocationRuleService.updateRule(id, rule);
    }

    @GetMapping("/{id}")
    public AssetClassAllocationRule getById(@PathVariable Long id) {
        return allocationRuleService.getRuleById(id);
    }

    @GetMapping("/investor/{investorId}")
    public List<AssetClassAllocationRule> getByInvestor(@PathVariable Long investorId) {
        return allocationRuleService.getRulesByInvestor(investorId);
    }

    @GetMapping("/active/{investorId}")
    public List<AssetClassAllocationRule> getActive(@PathVariable Long investorId) {
        return allocationRuleService.getActiveRules(investorId);
    }
}
