package com.example.demo.service.impl;

import com.example.demo.entity.AssetClassAllocationRule;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AssetClassAllocationRuleRepository;
import com.example.demo.service.AllocationRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllocationRuleServiceImpl implements AllocationRuleService {

    private final AssetClassAllocationRuleRepository allocationRuleRepository;

    public AllocationRuleServiceImpl(AssetClassAllocationRuleRepository allocationRuleRepository) {
        this.allocationRuleRepository = allocationRuleRepository;
    }

    @Override
    public AssetClassAllocationRule createRule(AssetClassAllocationRule rule) {
        if (rule.getTargetPercentage() < 0 || rule.getTargetPercentage() > 100) {
            throw new IllegalArgumentException("Target percentage must be between 0 and 100");
        }
        return allocationRuleRepository.save(rule);
    }

    @Override
    public AssetClassAllocationRule updateRule(Long id, AssetClassAllocationRule updatedRule) {
        return allocationRuleRepository.findById(id)
                .map(rule -> {
                    rule.setTargetPercentage(updatedRule.getTargetPercentage());
                    rule.setActive(updatedRule.getActive());
                    return allocationRuleRepository.save(rule);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Allocation rule not found with id: " + id));
    }

    @Override
    public List<AssetClassAllocationRule> getRulesByInvestor(Long investorId) {
        return allocationRuleRepository.findByInvestorId(investorId);
    }

    @Override
    public List<AssetClassAllocationRule> getActiveRules(Long investorId) {
        return allocationRuleRepository.findActiveRulesHql(investorId);
    }

    @Override
    public AssetClassAllocationRule getRuleById(Long id) {
        return allocationRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allocation rule not found with id: " + id));
    }

    @Override
    public List<AssetClassAllocationRule> getAllRules() {  // ← ADD THIS
        return allocationRuleRepository.findAll();
    }
}
