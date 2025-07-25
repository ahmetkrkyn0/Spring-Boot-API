package com.example.demo_junie.security.service;

import com.example.demo_junie.model.Role;
import com.example.demo_junie.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of UserDetailsService for Spring Security.
 * 
 * Note: In a real application, this would load users from a database.
 * For this demo, we're using an in-memory map of users.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    // In-memory user store for demo purposes
    private final Map<String, User> users = new HashMap<>();
    
    /**
     * Constructor that initializes some demo users.
     */
    public UserDetailsServiceImpl() {
        // Create a demo admin user
        User adminUser = new User(
                1L,
                "admin",
                "$2a$10$ixlPY3AAd4ty1l6E2IsQ9OFZi2ba9ZQE0bP7RFcGIWNhyFrrT3YUi", // password: admin123
                "admin@example.com",
                Arrays.asList(Role.ROLE_USER, Role.ROLE_ADMIN),
                true
        );
        
        // Create a demo regular user
        User regularUser = new User(
                2L,
                "user",
                "$2a$10$teJrCEnsxNT49ZpXU7n22O27aCGbVYYe/RG6/XxdWPJbOLZrRzJSW", // password: user123
                "user@example.com",
                Collections.singletonList(Role.ROLE_USER),
                true
        );
        
        // Add users to the in-memory store
        users.put(adminUser.getUsername(), adminUser);
        users.put(regularUser.getUsername(), regularUser);
    }
    
    /**
     * Loads a user by username.
     *
     * @param username the username to load
     * @return the UserDetails object
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        return UserDetailsImpl.build(user);
    }
    
    /**
     * Adds a new user to the in-memory store.
     *
     * @param user the user to add
     * @return the added user
     */
    public User addUser(User user) {
        // Generate a new ID if not provided
        if (user.getId() == null) {
            user.setId((long) (users.size() + 1));
        }
        
        // Add the user to the in-memory store
        users.put(user.getUsername(), user);
        
        return user;
    }
    
    /**
     * Checks if a username already exists.
     *
     * @param username the username to check
     * @return true if the username exists, false otherwise
     */
    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }
    
    /**
     * Checks if an email already exists.
     *
     * @param email the email to check
     * @return true if the email exists, false otherwise
     */
    public boolean existsByEmail(String email) {
        return users.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }
}