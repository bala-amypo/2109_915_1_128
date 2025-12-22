package com.example.demo.controller;

import com.example.demo.entity.HoldingRecord;
import com.example.demo.service.HoldingRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/holdings")
public class HoldingRecordController {
    private final HoldingRecordService service;

    public HoldingRecordController(HoldingRecordService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<HoldingRecord> create(@RequestBody HoldingRecord holding) {
        return ResponseEntity.ok(service.recordHolding(holding));
    }

    @GetMapping("/investor/{investorId}")
    public ResponseEntity<List<HoldingRecord>> getByInvestor(@PathVariable Long investorId) {
        return ResponseEntity.ok(service.getHoldingsByInvestor(investorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoldingRecord> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getHoldingById(id));
    }

    @GetMapping
    public ResponseEntity<List<HoldingRecord>> getAll() {
        return ResponseEntity.ok(service.getAllHoldings());
    }
}
