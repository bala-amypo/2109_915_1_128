package com.example.demo.controller;

import com.example.demo.entity.InvestorProfile;
import com.example.demo.service.InvestorProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investors")
public class InvestorProfileController {

    private final InvestorProfileService service;

    public InvestorProfileController(InvestorProfileService service) {
        this.service = service;
    }

    // GET
    @GetMapping("/{id}")
    public InvestorProfile getInvestor(@PathVariable Long id) {
        return service.getInvestorById(id);
    }

    // PUT
    @PutMapping("/{id}/status")
    public InvestorProfile updateStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {
        return service.updateInvestorStatus(id, active);
    }
    @PostMapping
public InvestorProfile createInvestor(@RequestBody InvestorProfile investor) {
    return service.createInvestor(investor);
}

}
