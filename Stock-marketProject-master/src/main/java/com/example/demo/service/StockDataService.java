package com.example.demo.service;

import com.example.demo.model.Stock;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.Stock;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface StockDataService {
    Stock getStockData(String symbol) throws IOException;
    List<HistoricalQuote> getHistoricalData(String symbol, Calendar from, Calendar to) throws IOException;
    Map<String, Stock> getMultipleStockData(String[] symbols) throws IOException;
    double calculateVolatility(List<HistoricalQuote> historicalData);
    double calculateMaxDrawdown(List<HistoricalQuote> historicalData);
    double calculateSharpeRatio(List<HistoricalQuote> historicalData, double riskFreeRate);
    double calculateBeta(List<HistoricalQuote> stockData, List<HistoricalQuote> marketData);
} 