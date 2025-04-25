package com.example.demo.controller;




import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    // http://localhost:9090/api/users/register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto userDto) {
        User existingUser = userService.findByEmail(userDto.getEmail());

        if (existingUser != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User savedUser = userService.save(userDto);
        return ResponseEntity.ok(savedUser);
    }
}
