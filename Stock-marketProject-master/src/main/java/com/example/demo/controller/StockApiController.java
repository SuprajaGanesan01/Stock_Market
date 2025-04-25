package com.example.demo.controller;


import com.example.demo.service.StockApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
public class StockApiController {

    @Autowired
    private StockApiService stockApiService;

//    @GetMapping("/{symbol}")
//    public String getStockInfo(@PathVariable String symbol) {
//        return stockApiService.getStockPrice(symbol);
//    }
    
    // http://localhost:9090/api/stock/{symbol}
    @GetMapping("/{symbol}")
    public double getStockInfo(@PathVariable String symbol) {
        return stockApiService.getStockPrice(symbol);
    }

}
