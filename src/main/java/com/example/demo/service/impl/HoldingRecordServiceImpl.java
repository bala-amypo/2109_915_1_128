package com.example.demo.service.impl;

import com.example.demo.entity.HoldingRecord;
import com.example.demo.repository.HoldingRecordRepository;
import com.example.demo.service.HoldingRecordService;

import java.util.List;
import java.util.Optional;

public class HoldingRecordServiceImpl implements HoldingRecordService {

    private final HoldingRecordRepository repository;

    public HoldingRecordServiceImpl(HoldingRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public HoldingRecord recordHolding(HoldingRecord holding) {
        if (holding.getCurrentValue() <= 0) {
            throw new IllegalArgumentException("Holding value must be > 0");
        }
        return repository.save(holding);
    }

    @Override
    public List<HoldingRecord> getHoldingsByInvestor(Long investorId) {
        return repository.findByInvestorId(investorId);
    }

    @Override
    public Optional<HoldingRecord> getHoldingById(Long id) {
        return repository.findById(id);
    }
}
