package com.example.demo.service;

import com.example.demo.model.Portfolio;
import com.example.demo.model.StockOrder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RiskAssessmentService {

    private static final double HIGH_CONCENTRATION_THRESHOLD = 0.20; // 20% threshold
    private static final double MAX_INVESTMENT_LIMIT = 1000000.0; // $1M limit

    public Map<String, Object> assessPortfolioRisk(Portfolio portfolio) {
        Map<String, Object> riskAssessment = new HashMap<>();
        
        // Calculate concentration risk
        Map<String, Double> concentrationRisk = calculateConcentrationRisk(portfolio);
        riskAssessment.put("concentrationRisk", concentrationRisk);
        
        // Calculate volatility
        double volatility = calculateVolatility(portfolio);
        riskAssessment.put("volatility", volatility);
        
        // Calculate Value at Risk (VaR)
        double var = calculateValueAtRisk(portfolio);
        riskAssessment.put("valueAtRisk", var);
        
        // Check investment limits
        boolean exceedsLimit = checkInvestmentLimit(portfolio);
        riskAssessment.put("exceedsInvestmentLimit", exceedsLimit);
        
        // Calculate risk level
        String riskLevel = determineRiskLevel(portfolio);
        riskAssessment.put("riskLevel", riskLevel);
        
        // Generate risk alerts
        List<String> riskAlerts = generateRiskAlerts(portfolio);
        riskAssessment.put("riskAlerts", riskAlerts);
        
        return riskAssessment;
    }

    private Map<String, Double> calculateConcentrationRisk(Portfolio portfolio) {
        Map<String, Double> concentrationMap = new HashMap<>();
        double totalValue = portfolio.getTotalValue();
        
        // Group orders by stock symbol and calculate concentration
        Map<String, Double> stockValues = portfolio.getStockOrders().stream()
            .filter(order -> "BUY".equals(order.getOrderType()))
            .collect(Collectors.groupingBy(
                StockOrder::getStockSymbol,
                Collectors.summingDouble(StockOrder::getInvestAmount)
            ));
        
        // Calculate concentration percentage for each stock
        stockValues.forEach((symbol, value) -> {
            double concentration = value / totalValue;
            concentrationMap.put(symbol, concentration);
        });
        
        return concentrationMap;
    }

    private double calculateVolatility(Portfolio portfolio) {
        // Calculate portfolio volatility using historical returns
        List<Double> returns = calculateHistoricalReturns(portfolio);
        if (returns.isEmpty()) {
            return 0.0;
        }
        
        double mean = returns.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double variance = returns.stream()
            .mapToDouble(r -> Math.pow(r - mean, 2))
            .average()
            .orElse(0.0);
        
        return Math.sqrt(variance);
    }

    private double calculateValueAtRisk(Portfolio portfolio) {
        // Calculate 95% Value at Risk
        List<Double> returns = calculateHistoricalReturns(portfolio);
        if (returns.isEmpty()) {
            return 0.0;
        }
        
        Collections.sort(returns);
        int index = (int) (returns.size() * 0.05);
        return returns.get(index) * portfolio.getTotalValue();
    }

    private boolean checkInvestmentLimit(Portfolio portfolio) {
        return portfolio.getTotalValue() > MAX_INVESTMENT_LIMIT;
    }

    private String determineRiskLevel(Portfolio portfolio) {
        double volatility = calculateVolatility(portfolio);
        double concentration = calculateMaxConcentration(portfolio);
        
        if (volatility > 0.3 || concentration > 0.4) {
            return "HIGH";
        } else if (volatility > 0.2 || concentration > 0.3) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }

    private List<String> generateRiskAlerts(Portfolio portfolio) {
        List<String> alerts = new ArrayList<>();
        
        // Check concentration
        Map<String, Double> concentration = calculateConcentrationRisk(portfolio);
        concentration.forEach((symbol, value) -> {
            if (value > HIGH_CONCENTRATION_THRESHOLD) {
                alerts.add("High concentration in " + symbol + ": " + String.format("%.1f%%", value * 100));
            }
        });
        
        // Check investment limit
        if (checkInvestmentLimit(portfolio)) {
            alerts.add("Portfolio exceeds maximum investment limit");
        }
        
        // Check volatility
        double volatility = calculateVolatility(portfolio);
        if (volatility > 0.3) {
            alerts.add("High portfolio volatility: " + String.format("%.1f%%", volatility * 100));
        }
        
        return alerts;
    }

    private double calculateMaxConcentration(Portfolio portfolio) {
        Map<String, Double> concentration = calculateConcentrationRisk(portfolio);
        return concentration.values().stream()
            .mapToDouble(Double::doubleValue)
            .max()
            .orElse(0.0);
    }

    private List<Double> calculateHistoricalReturns(Portfolio portfolio) {
        // This is a placeholder - you would need to implement actual historical data calculation
        // For now, return some dummy data
        return Arrays.asList(-0.02, 0.01, 0.03, -0.01, 0.02);
    }
} 