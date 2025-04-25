package com.example.demo.service;

import com.example.demo.Repository.StockRepository;
import com.example.demo.model.Stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepo;

    @Override
    public List<Stock> getAllStocks() {
        return stockRepo.findAll();
    }

    @Override
    public Stock getStockById(int id) {
        return stockRepo.findById(id).orElse(null);
    }

    @Override
    public Stock getStockBySymbol(String symbol) {
        return stockRepo.findByStockSymbol(symbol);
    }

    @Override
    public Stock saveStock(Stock stock) {
        return stockRepo.save(stock);
    }

    @Override
    public void deleteStock(int id) {
        stockRepo.deleteById(id);
    }

    @Override
    public void clearAllStocks() {
        stockRepo.deleteAll();
    }
}
