package com.example.demo.controller;

import com.example.demo.entity.RebalancingAlertRecord;
import com.example.demo.service.RebalancingAlertService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
public class RebalancingAlertController {

    private final RebalancingAlertService service;

    public RebalancingAlertController(RebalancingAlertService service) {
        this.service = service;
    }

    // GET
    @GetMapping("/{id}")
    public RebalancingAlertRecord getAlert(@PathVariable Long id) {
        return service.getAlertById(id);
    }

    // PUT
    @PutMapping("/{id}/resolve")
    public RebalancingAlertRecord resolveAlert(@PathVariable Long id) {
        return service.resolveAlert(id);
    }
}
