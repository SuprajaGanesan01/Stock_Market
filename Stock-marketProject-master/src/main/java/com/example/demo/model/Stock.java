package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Stock name is required")
    @Column(name = "stock_name")
    private String stock_name;

    @NotNull(message = "Stock price is required")
    @Positive(message = "Stock price must be positive")
    @Column(name = "stock_price")
    private Double stock_price;

    @NotBlank(message = "Stock symbol is required")
    @Column(name = "stock_symbol", unique = true)
    private String stockSymbol;

    @Column(name = "last_updated")
    private java.time.LocalDateTime lastUpdated;

    @Column(name = "market_cap")
    private Double marketCap;

    @Column(name = "volume")
    private Long volume;

    @Column(name = "change_percent")
    private Double changePercent;

    @Column(name = "volatility")
    private Double volatility;

    @Column(name = "max_drawdown")
    private Double maxDrawdown;

    @Column(name = "loss_percentage")
    private Double lossPercentage;

    @Column(name = "risk_level")
    private String riskLevel;

    // Default constructor
    public Stock() {
        this.lastUpdated = java.time.LocalDateTime.now();
    }

    // All-args constructor
    public Stock(String stock_name, Double stock_price, String stockSymbol) {
        this.stock_name = stock_name;
        this.stock_price = stock_price;
        this.stockSymbol = stockSymbol;
        this.lastUpdated = java.time.LocalDateTime.now();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public Double getStock_price() {
        return stock_price;
    }

    public void setStock_price(Double stock_price) {
        this.stock_price = stock_price;
        this.lastUpdated = java.time.LocalDateTime.now();
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public java.time.LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(java.time.LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(Double changePercent) {
        this.changePercent = changePercent;
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

    public Double getLossPercentage() {
        return lossPercentage;
    }

    public void setLossPercentage(Double lossPercentage) {
        this.lossPercentage = lossPercentage;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
