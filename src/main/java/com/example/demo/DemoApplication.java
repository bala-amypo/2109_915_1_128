package com.example.demo;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.repository.*;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.service.*;
import com.example.demo.service.impl.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // ---------- SERVICES ----------

    @Bean
    public InvestorProfileService investorProfileService(InvestorProfileRepository repo) {
        return new InvestorProfileServiceImpl(repo);
    }

    @Bean
    public AllocationRuleService allocationRuleService(AssetClassAllocationRuleRepository repo) {
        return new AllocationRuleServiceImpl(repo);
    }

    @Bean
    public HoldingRecordService holdingRecordService(HoldingRecordRepository repo) {
        return new HoldingRecordServiceImpl(repo);
    }

    @Bean
    public RebalancingAlertService rebalancingAlertService(RebalancingAlertRecordRepository repo) {
        return new RebalancingAlertServiceImpl(repo);
    }

    @Bean
    public AllocationSnapshotService allocationSnapshotService(
            AllocationSnapshotRecordRepository snapshotRepo,
            HoldingRecordRepository holdingRepo,
            AssetClassAllocationRuleRepository ruleRepo,
            RebalancingAlertRecordRepository alertRepo) {

        return new AllocationSnapshotServiceImpl(
                snapshotRepo, holdingRepo, ruleRepo, alertRepo);
    }

    // ---------- SECURITY ----------

    @Bean
    public CustomUserDetailsService userDetailsService(UserAccountRepository repo) {
        return new CustomUserDetailsService(repo);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder encoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder);

        return new ProviderManager(List.of(provider));
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider("secret-key", 3600000);
    }
}
