package com.example.demo.controller;

import com.example.demo.model.Stock;
import com.example.demo.service.StockApiService;
import com.example.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

	//

    private final StockService stockService;
    private final StockApiService stockAPIService;

    @Autowired
    public StockController(StockService stockService, StockApiService stockAPIService) {
        this.stockService = stockService;
        this.stockAPIService = stockAPIService;
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        try {
            List<Stock> stocks = stockService.getAllStocks();
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable int id) {
        try {
            Stock stock = stockService.getStockById(id);
            if (stock == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(stock);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
	//http://localhost:9090/api/stocks/create?stock_symbol=
    @PostMapping("/create")
    public ResponseEntity<?> createStock(@RequestParam String stock_symbol) {
        try {
            // Validate the stock symbol
            if (!stockAPIService.isStockValid(stock_symbol)) {
                return ResponseEntity.badRequest().body("Invalid stock symbol: " + stock_symbol);
            }

            // Get complete stock data
            Stock stock = stockAPIService.getStockData(stock_symbol);
            Stock savedStock = stockService.saveStock(stock);
            return ResponseEntity.ok(savedStock);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating stock: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable int id) {
        try {
            stockService.deleteStock(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/clearAll")
    public ResponseEntity<Void> clearAllStocks() {
        try {
            stockService.clearAllStocks();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
