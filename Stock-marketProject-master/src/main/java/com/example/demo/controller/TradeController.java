package com.example.demo.controller;

import com.example.demo.Repository.StockOrderRepository;
import com.example.demo.model.StockOrder;
//import com.example.demo.repository.StockOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    private final Logger logger = LoggerFactory.getLogger(TradeController.class);

    private final JdbcTemplate jdbcTemplate;
    private final StockOrderRepository stockOrderRepository;

    @Autowired
    public TradeController(StockOrderRepository stockOrderRepository, JdbcTemplate jdbcTemplate) {
        this.stockOrderRepository = stockOrderRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    // ✅ GET trade info and equity calculation
   // http://localhost:9090/api/trades?stockSymbol=TSLA&currentPrice=900.0&investedAmount=1000.0
    @GetMapping
    public ResponseEntity<?> getTradeDetails(
            @RequestParam String stockSymbol,
            @RequestParam double currentPrice,
            @RequestParam double investedAmount) {

        // Placeholder logic for investedStockPrice
        double investedStockPrice = 1.0; // TODO: Replace with real logic later

        // Calculate total equity
        double totalEquity = currentPrice / investedStockPrice * investedAmount;

        // Build response
        return ResponseEntity.ok(new TradeResponse(stockSymbol, currentPrice, investedAmount, totalEquity));
    }

    // ✅ DELETE (Sell) order by orderId
    @DeleteMapping("/sell")
    public ResponseEntity<?> sellOrder(@RequestParam Long orderId) {
        logger.info("Received sell request for orderId: {}", orderId);

        Optional<StockOrder> orderOptional = stockOrderRepository.findById(orderId);

        if (orderOptional.isPresent()) {
            stockOrderRepository.delete(orderOptional.get());
            return ResponseEntity.ok("Order with ID " + orderId + " sold successfully.");
        } else {
            return ResponseEntity.status(404).body("Order not found");
        }
    }

    // ✅ Internal DTO for trade response
    private static class TradeResponse {
        private final String stockSymbol;
        private final double currentPrice;
        private final double investedAmount;
        private final double totalEquity;

        public TradeResponse(String stockSymbol, double currentPrice, double investedAmount, double totalEquity) {
            this.stockSymbol = stockSymbol;
            this.currentPrice = currentPrice;
            this.investedAmount = investedAmount;
            this.totalEquity = totalEquity;
        }

        public String getStockSymbol() { return stockSymbol; }
        public double getCurrentPrice() { return currentPrice; }
        public double getInvestedAmount() { return investedAmount; }
        public double getTotalEquity() { return totalEquity; }
    }
}
