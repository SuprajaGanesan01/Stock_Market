package com.example.demo.controller;


import com.example.demo.model.StockOrder;
import com.example.demo.service.StockOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class StockOrderController {

    @Autowired
    private StockOrderService stockOrderService;
    
    // http://localhost:9090/api/orders
    @GetMapping
    public List<StockOrder> getAllOrders() {
        return stockOrderService.getAllOrders(); 
    }

   
}
