package com.example.demo.controller;

import com.example.demo.model.Portfolio;
import com.example.demo.service.PortfolioAnalyticsService;
import com.example.demo.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class PortfolioAnalyticsController {

    @Autowired
    private PortfolioAnalyticsService portfolioAnalyticsService;

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<?> analyzePortfolio(@PathVariable Long portfolioId) {
        try {
            Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
            Map<String, Object> analytics = portfolioAnalyticsService.analyzePortfolio(portfolio);
            return ResponseEntity.ok(analytics);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error analyzing portfolio: " + e.getMessage());
        }
    }

    @GetMapping("/performance/{portfolioId}")
    public ResponseEntity<?> getPortfolioPerformance(@PathVariable Long portfolioId) {
        try {
            Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
            Map<String, Object> analytics = portfolioAnalyticsService.analyzePortfolio(portfolio);
            return ResponseEntity.ok(Map.of(
                "totalReturn", analytics.get("totalReturn"),
                "annualizedReturn", analytics.get("annualizedReturn"),
                "performanceByStock", analytics.get("performanceByStock")
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error getting portfolio performance: " + e.getMessage());
        }
    }

    @GetMapping("/diversification/{portfolioId}")
    public ResponseEntity<?> getDiversificationAnalysis(@PathVariable Long portfolioId) {
        try {
            Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
            Map<String, Object> analytics = portfolioAnalyticsService.analyzePortfolio(portfolio);
            return ResponseEntity.ok(Map.of(
                "sectorAllocation", analytics.get("sectorAllocation"),
                "diversificationScore", analytics.get("diversificationScore")
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error getting diversification analysis: " + e.getMessage());
        }
    }

    @GetMapping("/recommendations/{portfolioId}")
    public ResponseEntity<?> getPortfolioRecommendations(@PathVariable Long portfolioId) {
        try {
            Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
            Map<String, Object> analytics = portfolioAnalyticsService.analyzePortfolio(portfolio);
            return ResponseEntity.ok(analytics.get("recommendations"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error getting portfolio recommendations: " + e.getMessage());
        }
    }
} 