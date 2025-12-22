package com.example.demo.service;

import com.example.demo.entity.InvestorProfile;

public interface InvestorProfileService {
    InvestorProfile getInvestorById(Long id);
    InvestorProfile updateInvestorStatus(Long id, boolean active);
}
