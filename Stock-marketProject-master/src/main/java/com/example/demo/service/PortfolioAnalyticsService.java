package com.example.demo.service;

import com.example.demo.model.Portfolio;
import java.util.Map;

public interface PortfolioAnalyticsService {
    Map<String, Object> analyzePortfolio(Portfolio portfolio);
} 