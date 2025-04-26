package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "portfolios")
public class Portfolio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    private String name;
    private Double totalValue;
    private Double initialInvestment;
    private Double currentValue;
    private Double totalReturn;
    private Double returnPercentage;
    
    // Risk metrics
    private Double volatility;
    private Double maxDrawdown;
    private Double sharpeRatio;
    private Double beta;
    
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<StockOrder> stockOrders = new ArrayList<>();
    
    public Portfolio() {
    }
    
    public Portfolio(User user, String name) {
        this.user = user;
        this.name = name;
        this.totalValue = 0.0;
        this.initialInvestment = 0.0;
        this.currentValue = 0.0;
        this.totalReturn = 0.0;
        this.returnPercentage = 0.0;
        this.volatility = 0.0;
        this.maxDrawdown = 0.0;
        this.sharpeRatio = 0.0;
        this.beta = 0.0;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Double getTotalValue() {
        return totalValue;
    }
    
    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }
    
    public Double getInitialInvestment() {
        return initialInvestment;
    }
    
    public void setInitialInvestment(Double initialInvestment) {
        this.initialInvestment = initialInvestment;
    }
    
    public Double getCurrentValue() {
        return currentValue;
    }
    
    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }
    
    public Double getTotalReturn() {
        return totalReturn;
    }
    
    public void setTotalReturn(Double totalReturn) {
        this.totalReturn = totalReturn;
    }
    
    public Double getReturnPercentage() {
        return returnPercentage;
    }
    
    public void setReturnPercentage(Double returnPercentage) {
        this.returnPercentage = returnPercentage;
    }
    
    public Double getVolatility() {
        return volatility;
    }
    
    public void setVolatility(Double volatility) {
        this.volatility = volatility;
    }
    
    public Double getMaxDrawdown() {
        return maxDrawdown;
    }
    
    public void setMaxDrawdown(Double maxDrawdown) {
        this.maxDrawdown = maxDrawdown;
    }
    
    public Double getSharpeRatio() {
        return sharpeRatio;
    }
    
    public void setSharpeRatio(Double sharpeRatio) {
        this.sharpeRatio = sharpeRatio;
    }
    
    public Double getBeta() {
        return beta;
    }
    
    public void setBeta(Double beta) {
        this.beta = beta;
    }
    
    public List<StockOrder> getStockOrders() {
        return stockOrders;
    }
    
    public void setStockOrders(List<StockOrder> stockOrders) {
        this.stockOrders = stockOrders;
    }
    
    public void addStockOrder(StockOrder order) {
        this.stockOrders.add(order);
        order.setPortfolio(this);
    }
    
    public void removeStockOrder(StockOrder order) {
        this.stockOrders.remove(order);
        order.setPortfolio(null);
    }
} 