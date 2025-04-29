package com.example.demo.service;

import com.example.demo.model.Portfolio;
import com.example.demo.model.StockOrder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PortfolioAnalyticsServiceImpl implements PortfolioAnalyticsService {

    @Override
    public Map<String, Object> analyzePortfolio(Portfolio portfolio) {
        Map<String, Object> analytics = new HashMap<>();
        
        // Performance metrics
        analytics.put("totalReturn", calculateTotalReturn(portfolio));
        analytics.put("annualizedReturn", calculateAnnualizedReturn(portfolio));
        analytics.put("performanceByStock", calculatePerformanceByStock(portfolio));
        
        // Diversification metrics
        analytics.put("sectorAllocation", calculateSectorAllocation(portfolio));
        analytics.put("diversificationScore", calculateDiversificationScore(portfolio));
        
        // Risk metrics
        analytics.put("riskMetrics", calculateRiskMetrics(portfolio));
        
        // Recommendations
        analytics.put("recommendations", generateRecommendations(portfolio));
        
        return analytics;
    }

    private double calculateTotalReturn(Portfolio portfolio) {
        double initialInvestment = portfolio.getInitialInvestment();
        double currentValue = portfolio.getCurrentValue();
        return ((currentValue - initialInvestment) / initialInvestment) * 100;
    }

    private double calculateAnnualizedReturn(Portfolio portfolio) {
        double totalReturn = calculateTotalReturn(portfolio);
        Date startDate = portfolio.getStockOrders().stream()
            .map(StockOrder::getOrderDate)
            .min(Date::compareTo)
            .orElse(new Date());
        
        LocalDateTime startDateTime = startDate.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();
        
        long days = ChronoUnit.DAYS.between(startDateTime, now);
        return Math.pow(1 + totalReturn/100, 365.0/days) - 1;
    }

    private Map<String, Double> calculatePerformanceByStock(Portfolio portfolio) {
        Map<String, Double> performance = new HashMap<>();
        
        portfolio.getStockOrders().stream()
            .filter(order -> "BUY".equals(order.getOrderType()))
            .collect(Collectors.groupingBy(
                StockOrder::getStockSymbol,
                Collectors.summingDouble(StockOrder::getInvestAmount)
            ))
            .forEach((symbol, invested) -> {
                double currentValue = calculateCurrentStockValue(symbol, portfolio);
                double returnPercentage = ((currentValue - invested) / invested) * 100;
                performance.put(symbol, returnPercentage);
            });
        
        return performance;
    }

    private Map<String, Double> calculateSectorAllocation(Portfolio portfolio) {
        // This is a placeholder - you would need to implement actual sector data
        Map<String, Double> allocation = new HashMap<>();
        allocation.put("Technology", 40.0);
        allocation.put("Finance", 30.0);
        allocation.put("Healthcare", 20.0);
        allocation.put("Others", 10.0);
        return allocation;
    }

    private double calculateDiversificationScore(Portfolio portfolio) {
        Map<String, Double> allocation = calculateSectorAllocation(portfolio);
        double sumSquared = allocation.values().stream()
            .mapToDouble(value -> value * value)
            .sum();
        return 1.0 / sumSquared; // Herfindahl-Hirschman Index
    }

    private Map<String, Object> calculateRiskMetrics(Portfolio portfolio) {
        Map<String, Object> riskMetrics = new HashMap<>();
        riskMetrics.put("volatility", calculateVolatility(portfolio));
        riskMetrics.put("maxDrawdown", calculateMaxDrawdown(portfolio));
        riskMetrics.put("sharpeRatio", calculateSharpeRatio(portfolio));
        return riskMetrics;
    }

    private List<String> generateRecommendations(Portfolio portfolio) {
        List<String> recommendations = new ArrayList<>();
        
        // Check diversification
        double diversificationScore = calculateDiversificationScore(portfolio);
        if (diversificationScore < 0.5) {
            recommendations.add("Consider diversifying your portfolio across more sectors");
        }
        
        // Check performance
        double totalReturn = calculateTotalReturn(portfolio);
        if (totalReturn < 0) {
            recommendations.add("Review underperforming stocks and consider rebalancing");
        }
        
        // Check risk level
        Map<String, Object> riskMetrics = calculateRiskMetrics(portfolio);
        double volatility = (double) riskMetrics.get("volatility");
        if (volatility > 0.2) {
            recommendations.add("High volatility detected. Consider adding more stable stocks");
        }
        
        return recommendations;
    }

    private double calculateCurrentStockValue(String symbol, Portfolio portfolio) {
        // This is a placeholder - you would need to implement actual current value calculation
        return portfolio.getStockOrders().stream()
            .filter(order -> symbol.equals(order.getStockSymbol()) && "BUY".equals(order.getOrderType()))
            .mapToDouble(StockOrder::getInvestAmount)
            .sum() * 1.1; // Assuming 10% return for demonstration
    }

    private double calculateVolatility(Portfolio portfolio) {
        // This is a placeholder - you would need to implement actual volatility calculation
        return 0.15;
    }

    private double calculateMaxDrawdown(Portfolio portfolio) {
        // This is a placeholder - you would need to implement actual drawdown calculation
        return 0.1;
    }

    private double calculateSharpeRatio(Portfolio portfolio) {
        // This is a placeholder - you would need to implement actual Sharpe ratio calculation
        return 1.5;
    }
} 