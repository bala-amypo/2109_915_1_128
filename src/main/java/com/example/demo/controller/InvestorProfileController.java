package com.example.demo.controller;

import com.example.demo.entity.InvestorProfile;
import com.example.demo.service.InvestorProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/investors")
public class InvestorProfileController {
    private final InvestorProfileService service;

    public InvestorProfileController(InvestorProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InvestorProfile> create(@RequestBody InvestorProfile investor) {
        return ResponseEntity.ok(service.createInvestor(investor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestorProfile> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getInvestorById(id));
    }

    @GetMapping
    public ResponseEntity<List<InvestorProfile>> getAll() {
        return ResponseEntity.ok(service.getAllInvestors());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<InvestorProfile> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(service.updateInvestorStatus(id, active));
    }

    @GetMapping("/lookup/{investorId}")
    public ResponseEntity<InvestorProfile> findByInvestorId(@PathVariable String investorId) {
        return ResponseEntity.ok(service.findByInvestorId(investorId));
    }
}
