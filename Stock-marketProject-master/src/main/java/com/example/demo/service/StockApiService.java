package com.example.demo.service;

import com.example.demo.model.Stock;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;
import yahoofinance.Stock;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Service
public class StockApiService {

    public Stock getStockData(String symbol) {
        try {
            // Add .NS suffix for Indian stocks
            String nseSymbol = symbol + ".NS";
            yahoofinance.Stock yahooStock = YahooFinance.get(nseSymbol);
            
            if (yahooStock == null || yahooStock.getQuote() == null) {
                throw new RuntimeException("Stock data not available for symbol: " + symbol);
            }

            Stock stock = new Stock();
            stock.setStockSymbol(symbol);
            stock.setStock_name(yahooStock.getName());
            stock.setStock_price(yahooStock.getQuote().getPrice().doubleValue());
            
            // Set additional fields
            if (yahooStock.getQuote().getMarketCap() != null) {
                stock.setMarketCap(yahooStock.getQuote().getMarketCap().doubleValue());
            }
            if (yahooStock.getQuote().getVolume() != null) {
                stock.setVolume(yahooStock.getQuote().getVolume());
            }
            if (yahooStock.getQuote().getChangeInPercent() != null) {
                stock.setChangePercent(yahooStock.getQuote().getChangeInPercent().doubleValue());
            }

            // Calculate risk indicators
            calculateRiskIndicators(stock, yahooStock);
            
            return stock;
        } catch (IOException e) {
            throw new RuntimeException("Failed to get stock data for symbol: " + symbol, e);
        }
    }

    private void calculateRiskIndicators(Stock stock, yahoofinance.Stock yahooStock) throws IOException {
        // Get historical data for the last year
        Calendar from = Calendar.getInstance();
        from.add(Calendar.YEAR, -1);
        Calendar to = Calendar.getInstance();
        List<HistoricalQuote> history = yahooStock.getHistory(from, to, Interval.DAILY);

        if (!history.isEmpty()) {
            // Calculate daily returns
            double[] returns = new double[history.size() - 1];
            for (int i = 1; i < history.size(); i++) {
                double previousPrice = history.get(i - 1).getClose().doubleValue();
                double currentPrice = history.get(i).getClose().doubleValue();
                returns[i - 1] = (currentPrice - previousPrice) / previousPrice;
            }

            // Calculate volatility (standard deviation of returns)
            double mean = calculateMean(returns);
            double variance = calculateVariance(returns, mean);
            double volatility = Math.sqrt(variance);
            stock.setVolatility(volatility);

            // Calculate maximum drawdown
            double maxDrawdown = calculateMaxDrawdown(history);
            stock.setMaxDrawdown(maxDrawdown);

            // Calculate loss percentage
            double currentPrice = yahooStock.getQuote().getPrice().doubleValue();
            double yearHigh = history.stream()
                .mapToDouble(quote -> quote.getHigh().doubleValue())
                .max()
                .orElse(currentPrice);
            double lossPercentage = ((yearHigh - currentPrice) / yearHigh) * 100;
            stock.setLossPercentage(lossPercentage);

            // Calculate risk level
            String riskLevel = determineRiskLevel(volatility, maxDrawdown, lossPercentage);
            stock.setRiskLevel(riskLevel);
        }
    }

    private double calculateMean(double[] values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    private double calculateVariance(double[] values, double mean) {
        double sumSquaredDiff = 0.0;
        for (double value : values) {
            sumSquaredDiff += Math.pow(value - mean, 2);
        }
        return sumSquaredDiff / values.length;
    }

    private double calculateMaxDrawdown(List<HistoricalQuote> history) {
        double maxDrawdown = 0.0;
        double peak = history.get(0).getHigh().doubleValue();

        for (HistoricalQuote quote : history) {
            double currentPrice = quote.getClose().doubleValue();
            if (currentPrice > peak) {
                peak = currentPrice;
            }
            double drawdown = (peak - currentPrice) / peak;
            maxDrawdown = Math.max(maxDrawdown, drawdown);
        }

        return maxDrawdown * 100; // Convert to percentage
    }

    private String determineRiskLevel(double volatility, double maxDrawdown, double lossPercentage) {
        if (volatility > 0.3 || maxDrawdown > 30 || lossPercentage > 20) {
            return "HIGH";
        } else if (volatility > 0.2 || maxDrawdown > 20 || lossPercentage > 10) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }

    public boolean isStockValid(String symbol) {
        try {
            String nseSymbol = symbol + ".NS";
            yahoofinance.Stock stock = YahooFinance.get(nseSymbol);
            return stock != null && stock.getQuote() != null;
        } catch (IOException e) {
            return false;
        }
    }

    public double getStockPrice(String symbol) {
        try {
            String nseSymbol = symbol + ".NS";
            yahoofinance.Stock stock = YahooFinance.get(nseSymbol);
            if (stock == null || stock.getQuote() == null) {
                throw new RuntimeException("Stock data not available for symbol: " + symbol);
            }
            return stock.getQuote().getPrice().doubleValue();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get stock price for symbol: " + symbol, e);
        }
    }

    public String getStockName(String symbol) {
        try {
            String nseSymbol = symbol + ".NS";
            yahoofinance.Stock stock = YahooFinance.get(nseSymbol);
            if (stock == null) {
                throw new RuntimeException("Stock data not available for symbol: " + symbol);
            }
            return stock.getName() != null ? stock.getName() : symbol;
        } catch (IOException e) {
            return symbol;
        }
    }
}
