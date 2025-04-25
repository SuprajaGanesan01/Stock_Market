package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String stock_name;

    private Double stock_price;

    @Column(name = "stock_symbol")
    private String stockSymbol;

    // Default constructor
    public Stock() {
        super();
    }

    // All-args constructor
    public Stock(int id, String stock_name, Double stock_price, String stockSymbol) {
        super();
        this.id = id;
        this.stock_name = stock_name;
        this.stock_price = stock_price;
        this.stockSymbol = stockSymbol;
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
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
}
