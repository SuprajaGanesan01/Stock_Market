package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    
    // Custom query to find all distinct stock symbols from the database
    @Query("SELECT DISTINCT stock.stockSymbol FROM Stock stock")
    List<String> findAllStockSymbols();

    // Custom query to find a stock by its symbol
    Stock findByStockSymbol(String symbol);
}