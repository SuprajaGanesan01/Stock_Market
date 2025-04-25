package com.example.demo.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.StockOrder;


@Repository
public interface StockOrderRepository extends JpaRepository<StockOrder,Long> {
	List<StockOrder> findByUserEmail(String userEmail);

    // Custom query to find all stock orders by order date
    List<StockOrder> findByOrderDate(Date orderDate);

    // Custom query to find all stock orders by stock symbol
    List<StockOrder> findByStockSymbol(String stockSymbol);

    // Custom query to get the stock price by stock symbol
    double getStockPriceByStockSymbol(String stockSymbol);

}
