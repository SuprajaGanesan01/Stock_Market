package com.example.demo.service;

import com.example.demo.Repository.PortfolioRepository;
import com.example.demo.model.Portfolio;
import com.example.demo.model.StockOrder;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private StockDataService stockDataService;

    public Portfolio createPortfolio(User user, String name) {
        Portfolio portfolio = new Portfolio(user, name);
        return portfolioRepository.save(portfolio);
    }

    public List<Portfolio> getUserPortfolios(User user) {
        return portfolioRepository.findByUser(user);
    }

    public Portfolio getPortfolioById(Long id) {
        return portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
    }

    public Portfolio updatePortfolioValue(Portfolio portfolio) {
        double totalValue = 0.0;
        for (StockOrder order : portfolio.getStockOrders()) {
            if ("BUY".equals(order.getOrderType())) {
                totalValue += order.getInvestAmount();
            } else {
                totalValue -= order.getInvestAmount();
            }
        }
        portfolio.setTotalValue(totalValue);
        portfolio.setCurrentValue(totalValue);
        
        // Calculate returns
        double initialInvestment = portfolio.getInitialInvestment();
        if (initialInvestment > 0) {
            double totalReturn = totalValue - initialInvestment;
            double returnPercentage = (totalReturn / initialInvestment) * 100;
            portfolio.setTotalReturn(totalReturn);
            portfolio.setReturnPercentage(returnPercentage);
        }
        
        return portfolioRepository.save(portfolio);
    }

    public Portfolio addStockOrder(Portfolio portfolio, StockOrder order) {
        portfolio.addStockOrder(order);
        return updatePortfolioValue(portfolio);
    }

    public Portfolio removeStockOrder(Portfolio portfolio, StockOrder order) {
        portfolio.removeStockOrder(order);
        return updatePortfolioValue(portfolio);
    }

    public void deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
    }

    public Portfolio calculateRiskMetrics(Portfolio portfolio) throws IOException {
        // Get historical data for each stock in the portfolio
        Calendar from = Calendar.getInstance();
        from.add(Calendar.YEAR, -1); // Get 1 year of historical data
        Calendar to = Calendar.getInstance();

        // Calculate portfolio-level risk metrics
        double totalVolatility = 0.0;
        double totalMaxDrawdown = 0.0;
        double totalSharpeRatio = 0.0;
        double totalBeta = 0.0;
        int stockCount = 0;

        for (StockOrder order : portfolio.getStockOrders()) {
            if ("BUY".equals(order.getOrderType())) {
                List<HistoricalQuote> historicalData = stockDataService.getHistoricalData(
                    order.getStockSymbol(), from, to);

                // Calculate individual stock metrics
                double volatility = stockDataService.calculateVolatility(historicalData);
                double maxDrawdown = stockDataService.calculateMaxDrawdown(historicalData);
                double sharpeRatio = stockDataService.calculateSharpeRatio(historicalData, 0.02);

                // Get market data (using NIFTY 50 as benchmark)
                List<HistoricalQuote> marketData = stockDataService.getHistoricalData("^NSEI", from, to);
                double beta = stockDataService.calculateBeta(historicalData, marketData);

                // Add to portfolio totals
                totalVolatility += volatility;
                totalMaxDrawdown += maxDrawdown;
                totalSharpeRatio += sharpeRatio;
                totalBeta += beta;
                stockCount++;
            }
        }

        // Calculate portfolio averages
        if (stockCount > 0) {
            portfolio.setVolatility(totalVolatility / stockCount);
            portfolio.setMaxDrawdown(totalMaxDrawdown / stockCount);
            portfolio.setSharpeRatio(totalSharpeRatio / stockCount);
            portfolio.setBeta(totalBeta / stockCount);
        }

        return portfolioRepository.save(portfolio);
    }
} 