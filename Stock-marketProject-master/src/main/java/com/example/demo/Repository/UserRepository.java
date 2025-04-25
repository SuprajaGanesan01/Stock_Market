package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
	// Find a user by their email
    User findByEmail(String email);

    // Find a list of users whose email contains the given string (case-insensitive)
    List<User> findByEmailContainingIgnoreCase(String email);

    // Find a list of users whose first name contains the given string (case-insensitive)
    List<User> findByFirstNameContainingIgnoreCase(String firstName);
	
	

}
