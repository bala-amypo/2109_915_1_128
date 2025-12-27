package com.example.demo.service.impl;

import com.example.demo.entity.AssetClassAllocationRule;
import com.example.demo.repository.AssetClassAllocationRuleRepository;
import com.example.demo.service.AllocationRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service   // ⭐⭐⭐ THIS ANNOTATION IS REQUIRED
public class AllocationRuleServiceImpl implements AllocationRuleService {

    private final AssetClassAllocationRuleRepository repository;

    public AllocationRuleServiceImpl(AssetClassAllocationRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public AssetClassAllocationRule saveRule(AssetClassAllocationRule rule) {
        return repository.save(rule);
    }

    @Override
    public List<AssetClassAllocationRule> getAllRules() {
        return repository.findAll();
    }

    @Override
    public AssetClassAllocationRule getRuleById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
    }

    @Override
    public void deleteRule(Long id) {
        repository.deleteById(id);
    }
}
