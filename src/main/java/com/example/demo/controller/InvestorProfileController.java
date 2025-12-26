package com.example.demo.controller;

import com.example.demo.entity.InvestorProfile;
import com.example.demo.service.InvestorProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investors")
public class InvestorProfileController {

    private final InvestorProfileService investorProfileService;

    public InvestorProfileController(InvestorProfileService investorProfileService) {
        this.investorProfileService = investorProfileService;
    }

    @PostMapping
    public ResponseEntity<InvestorProfile> create(@RequestBody InvestorProfile investor) {
        return ResponseEntity.ok(investorProfileService.createInvestor(investor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestorProfile> getById(@PathVariable Long id) {
        return ResponseEntity.ok(investorProfileService.getInvestorById(id));
    }

    @GetMapping
    public ResponseEntity<List<InvestorProfile>> getAll() {
        return ResponseEntity.ok(investorProfileService.getAllInvestors());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<InvestorProfile> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(investorProfileService.updateInvestorStatus(id, active));
    }

    @GetMapping("/lookup/{investorId}")
    public ResponseEntity<InvestorProfile> findByInvestorId(@PathVariable String investorId) {
        return investorProfileService.findByInvestorId(investorId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
