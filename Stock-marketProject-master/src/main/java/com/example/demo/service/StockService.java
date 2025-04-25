package com.example.demo.service;

import com.example.demo.model.Stock;

import java.util.List;

public interface StockService {
    List<Stock> getAllStocks();
    Stock getStockById(int id);
    Stock saveStock(Stock stock);
    void deleteStock(int id);
    void clearAllStocks();
    Stock getStockBySymbol(String symbol);
}
