package com.example.demo.service;

import com.example.demo.model.Stock;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class StockDataServiceImpl implements StockDataService {

    @Override
    public Stock getStockData(String symbol) throws IOException {
        // Add .NS suffix for Indian stocks
        String nseSymbol = symbol + ".NS";
        yahoofinance.Stock yahooStock = YahooFinance.get(nseSymbol);
        
        Stock stock = new Stock();
        stock.setStockSymbol(symbol);
        stock.setStock_name(yahooStock.getName());
        stock.setStock_price(yahooStock.getQuote().getPrice().doubleValue());
        
        return stock;
    }

    @Override
    public List<HistoricalQuote> getHistoricalData(String symbol, Calendar from, Calendar to) throws IOException {
        // Add .NS suffix for Indian stocks
        String nseSymbol = symbol + ".NS";
        yahoofinance.Stock yahooStock = YahooFinance.get(nseSymbol);
        return yahooStock.getHistory(from, to, Interval.DAILY);
    }

    @Override
    public Map<String, yahoofinance.Stock> getMultipleStockData(String[] symbols) throws IOException {
        // Add .NS suffix for all Indian stock symbols
        String[] nseSymbols = new String[symbols.length];
        for (int i = 0; i < symbols.length; i++) {
            nseSymbols[i] = symbols[i] + ".NS";
        }
        return YahooFinance.get(nseSymbols);
    }

    @Override
    public double calculateVolatility(List<HistoricalQuote> historicalData) {
        if (historicalData == null || historicalData.isEmpty()) {
            return 0.0;
        }

        // Calculate daily returns
        double[] returns = calculateReturns(historicalData);

        // Calculate mean return
        double mean = calculateMean(returns);

        // Calculate variance
        double variance = 0.0;
        for (double return_ : returns) {
            variance += Math.pow(return_ - mean, 2);
        }
        variance /= returns.length;

        // Calculate standard deviation (volatility)
        return Math.sqrt(variance);
    }

    @Override
    public double calculateMaxDrawdown(List<HistoricalQuote> historicalData) {
        if (historicalData == null || historicalData.isEmpty()) {
            return 0.0;
        }

        double maxDrawdown = 0.0;
        double peak = historicalData.get(0).getClose().doubleValue();

        for (HistoricalQuote quote : historicalData) {
            double currentPrice = quote.getClose().doubleValue();
            if (currentPrice > peak) {
                peak = currentPrice;
            }
            double drawdown = (peak - currentPrice) / peak;
            maxDrawdown = Math.max(maxDrawdown, drawdown);
        }

        return maxDrawdown;
    }

    @Override
    public double calculateSharpeRatio(List<HistoricalQuote> historicalData, double riskFreeRate) {
        if (historicalData == null || historicalData.isEmpty()) {
            return 0.0;
        }

        // Calculate daily returns
        double[] returns = calculateReturns(historicalData);

        // Calculate mean return
        double meanReturn = calculateMean(returns);

        // Calculate standard deviation
        double variance = 0.0;
        for (double return_ : returns) {
            variance += Math.pow(return_ - meanReturn, 2);
        }
        double stdDev = Math.sqrt(variance / returns.length);

        // Calculate Sharpe ratio
        return (meanReturn - riskFreeRate) / stdDev;
    }

    @Override
    public double calculateBeta(List<HistoricalQuote> stockData, List<HistoricalQuote> marketData) {
        if (stockData == null || marketData == null || stockData.isEmpty() || marketData.isEmpty()) {
            return 0.0;
        }

        // Calculate daily returns for both stock and market
        double[] stockReturns = calculateReturns(stockData);
        double[] marketReturns = calculateReturns(marketData);

        // Calculate covariance and market variance
        double covariance = 0.0;
        double marketVariance = 0.0;
        double stockMean = calculateMean(stockReturns);
        double marketMean = calculateMean(marketReturns);

        for (int i = 0; i < stockReturns.length; i++) {
            covariance += (stockReturns[i] - stockMean) * (marketReturns[i] - marketMean);
            marketVariance += Math.pow(marketReturns[i] - marketMean, 2);
        }

        covariance /= stockReturns.length;
        marketVariance /= marketReturns.length;

        // Calculate beta
        return covariance / marketVariance;
    }

    private double[] calculateReturns(List<HistoricalQuote> data) {
        double[] returns = new double[data.size() - 1];
        for (int i = 1; i < data.size(); i++) {
            double previousPrice = data.get(i - 1).getClose().doubleValue();
            double currentPrice = data.get(i).getClose().doubleValue();
            returns[i - 1] = (currentPrice - previousPrice) / previousPrice;
        }
        return returns;
    }

    private double calculateMean(double[] values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }
} 