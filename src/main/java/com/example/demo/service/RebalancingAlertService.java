package com.example.demo.service;

import com.example.demo.entity.RebalancingAlertRecord;

public interface RebalancingAlertService {
    RebalancingAlertRecord getAlertById(Long id);
    RebalancingAlertRecord resolveAlert(Long id);
    RebalancingAlertRecord createAlert(RebalancingAlertRecord alert);

}
