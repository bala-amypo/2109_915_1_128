package com.example.demo.service;

import com.example.demo.entity.AssetClassAllocationRule;

import java.util.List;

public interface AllocationRuleService {

    AssetClassAllocationRule saveRule(AssetClassAllocationRule rule);

    List<AssetClassAllocationRule> getAllRules();

    AssetClassAllocationRule getRuleById(Long id);

    void deleteRule(Long id);
}
