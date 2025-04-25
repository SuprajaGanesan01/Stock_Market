package com.example.demo.controller;

import com.example.demo.model.Stock;
import com.example.demo.service.StockApiService;
import com.example.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/{id}")
    public Stock getStockById(@PathVariable int id) {
        return stockService.getStockById(id);
    }
    
	//http://localhost:9090/api/stocks/create?stock_symbol=
    @PostMapping("/create")
    public Stock createStock(@RequestParam String stock_symbol) throws IOException {
        String name = stockAPIService.getStockName(stock_symbol);
        double price = stockAPIService.getStockPrice(stock_symbol);

        Stock stock = new Stock();
        stock.setStockSymbol(stock_symbol);
        stock.setStock_name(name);
        stock.setStock_price(price);

        return stockService.saveStock(stock);
    }

    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable int id) {
        stockService.deleteStock(id);
    }

    @PostMapping("/clearAll")
    public void clearAllStocks() {
        stockService.clearAllStocks();
    }
}
