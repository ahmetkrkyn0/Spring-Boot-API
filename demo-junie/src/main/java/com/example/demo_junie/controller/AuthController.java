package com.example.demo_junie.controller;

import com.example.demo_junie.security.dto.JwtResponse;
import com.example.demo_junie.security.dto.LoginRequest;
import com.example.demo_junie.security.dto.RegisterRequest;
import com.example.demo_junie.security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for authentication operations.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param loginRequest the login request
     * @return the JWT response
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * Registers a new user.
     *
     * @param registerRequest the register request
     * @return a success message if the user was registered successfully, an error message otherwise
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (authService.registerUser(registerRequest)) {
            return ResponseEntity.ok("User registered successfully!");
        } else {
            return ResponseEntity.badRequest().body("Error: Username or email is already taken!");
        }
    }
}