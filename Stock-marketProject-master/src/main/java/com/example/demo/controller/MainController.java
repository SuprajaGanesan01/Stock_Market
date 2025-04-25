package com.example.demo.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MainController {

	// http://localhost:9090/api
    @GetMapping
    public ResponseEntity<String> root() {
        return ResponseEntity.ok("Welcome to the Trade Stock API Home Page");
    }

    // http://localhost:9090/api/login
    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Login endpoint - integrate this with Spring Security for actual auth");
    }

    // http://localhost:9090/api/user
    @GetMapping("/user")
    public ResponseEntity<String> userIndex() {
        return ResponseEntity.ok("User Dashboard - API Endpoint");
    }
}
