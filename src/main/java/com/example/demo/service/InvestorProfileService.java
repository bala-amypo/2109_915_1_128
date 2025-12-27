package com.example.demo.service;

import com.example.demo.entity.InvestorProfile;

import java.util.List;
import java.util.Optional;

public interface InvestorProfileService {

    InvestorProfile createInvestor(InvestorProfile investor);

    InvestorProfile getInvestorById(Long id);

    List<InvestorProfile> getAllInvestors();

    InvestorProfile updateInvestorStatus(Long id, boolean active);

    Optional<InvestorProfile> findByInvestorId(String investorId);
}
