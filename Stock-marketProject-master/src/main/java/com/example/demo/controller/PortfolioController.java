package com.example.demo.controller;

import com.example.demo.model.Portfolio;
import com.example.demo.model.StockOrder;
import com.example.demo.model.User;
import com.example.demo.service.PortfolioService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Portfolio> createPortfolio(
            @RequestBody Portfolio portfolio,
            Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + authentication.getName());
        }
        Portfolio newPortfolio = portfolioService.createPortfolio(user, portfolio.getName());
        return ResponseEntity.ok(newPortfolio);
    }

    @GetMapping
    public ResponseEntity<List<Portfolio>> getUserPortfolios(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        List<Portfolio> portfolios = portfolioService.getUserPortfolios(user);
        return ResponseEntity.ok(portfolios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable Long id) {
        Portfolio portfolio = portfolioService.getPortfolioById(id);
        return ResponseEntity.ok(portfolio);
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<Portfolio> addStockOrder(
            @PathVariable Long id,
            @RequestBody StockOrder order) {
        Portfolio portfolio = portfolioService.getPortfolioById(id);
        Portfolio updatedPortfolio = portfolioService.addStockOrder(portfolio, order);
        return ResponseEntity.ok(updatedPortfolio);
    }

    @DeleteMapping("/{id}/orders/{orderId}")
    public ResponseEntity<Portfolio> removeStockOrder(
            @PathVariable Long id,
            @PathVariable Long orderId) {
        Portfolio portfolio = portfolioService.getPortfolioById(id);
        StockOrder order = portfolio.getStockOrders().stream()
                .filter(o -> o.getId() == orderId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Portfolio updatedPortfolio = portfolioService.removeStockOrder(portfolio, order);
        return ResponseEntity.ok(updatedPortfolio);
    }

    @GetMapping("/{id}/risk-metrics")
    public ResponseEntity<Portfolio> calculateRiskMetrics(@PathVariable Long id) {
        try {
            Portfolio portfolio = portfolioService.getPortfolioById(id);
            Portfolio updatedPortfolio = portfolioService.calculateRiskMetrics(portfolio);
            return ResponseEntity.ok(updatedPortfolio);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable Long id) {
        portfolioService.deletePortfolio(id);
        return ResponseEntity.ok().build();
    }
} 