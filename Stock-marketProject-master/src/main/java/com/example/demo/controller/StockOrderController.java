package com.example.demo.controller;

import com.example.demo.model.StockOrder;
import com.example.demo.service.StockOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class StockOrderController {

    @Autowired
    private StockOrderService stockOrderService;

    @PostMapping
    public ResponseEntity<StockOrder> createOrder(@RequestBody StockOrder order) {
        return ResponseEntity.ok(stockOrderService.createOrder(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockOrder> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(stockOrderService.getOrder(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StockOrder>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(stockOrderService.getUserOrders(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        stockOrderService.cancelOrder(id);
        return ResponseEntity.ok().build();
    }
} 