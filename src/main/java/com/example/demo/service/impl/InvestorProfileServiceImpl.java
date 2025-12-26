package com.example.demo.service.impl;

import com.example.demo.entity.InvestorProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.InvestorProfileRepository;
import java.util.List;
import java.util.Optional;

public class InvestorProfileServiceImpl {

    private final InvestorProfileRepository repo;

    public InvestorProfileServiceImpl(InvestorProfileRepository repo) {
        this.repo = repo;
    }

    public InvestorProfile createInvestor(InvestorProfile investor) {
        return repo.save(investor);
    }

    public InvestorProfile getInvestorById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investor not found " + id));
    }

    public List<InvestorProfile> getAllInvestors() {
        return repo.findAll();
    }

    public InvestorProfile updateInvestorStatus(Long id, boolean active) {
        InvestorProfile inv = getInvestorById(id);
        inv.setActive(active);
        return repo.save(inv);
    }

    public Optional<InvestorProfile> findByInvestorId(String investorId) {
        return repo.findByInvestorId(investorId);
    }
}
