package com.example.demo.controller;

import com.example.demo.model.Portfolio;
import com.example.demo.service.PortfolioService;
import com.example.demo.service.RiskAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/risk")
public class RiskAssessmentController {

    @Autowired
    private RiskAssessmentService riskAssessmentService;

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<?> assessPortfolioRisk(@PathVariable Long portfolioId) {
        try {
            Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
            Map<String, Object> riskAssessment = riskAssessmentService.assessPortfolioRisk(portfolio);
            return ResponseEntity.ok(riskAssessment);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error assessing portfolio risk: " + e.getMessage());
        }
    }

    @GetMapping("/alerts/{portfolioId}")
    public ResponseEntity<?> getRiskAlerts(@PathVariable Long portfolioId) {
        try {
            Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
            Map<String, Object> riskAssessment = riskAssessmentService.assessPortfolioRisk(portfolio);
            return ResponseEntity.ok(riskAssessment.get("riskAlerts"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error getting risk alerts: " + e.getMessage());
        }
    }
} 