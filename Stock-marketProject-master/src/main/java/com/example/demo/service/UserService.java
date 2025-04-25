package com.example.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.model.User;

import java.util.List;

/**
 * The UserService interface defines methods for managing user data,
 * including registration, deletion, and search operations.
 * It extends Spring Security's UserDetailsService for authentication support.
 */
public interface UserService extends UserDetailsService {

    /**
     * Find a user by their email address.
     *
     * @param email the user's email
     * @return the User object, or null if not found
     */
    User findByEmail(String email);

    /**
     * Save a new user registration to the database.
     *
     * @param registration the DTO containing user registration data
     * @return the saved User object
     */
    User save(UserRegistrationDto registration);

    /**
     * Delete a user by their unique ID.
     *
     * @param id the user's ID
     */
    void deleteUser(Long id);

    /**
     * Search for users by their email address.
     *
     * @param email the email to search
     * @return a list of matching users
     */
    List<User> searchByEmail(String email);

    /**
     * Search for users by their first name.
     *
     * @param firstName the first name to search
     * @return a list of matching users
     */
    List<User> searchByFirstName(String firstName);
}
