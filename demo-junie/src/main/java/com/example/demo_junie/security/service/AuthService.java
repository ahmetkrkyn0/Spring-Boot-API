package com.example.demo_junie.security.service;

import com.example.demo_junie.model.Role;
import com.example.demo_junie.model.User;
import com.example.demo_junie.security.JwtUtils;
import com.example.demo_junie.security.dto.JwtResponse;
import com.example.demo_junie.security.dto.LoginRequest;
import com.example.demo_junie.security.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Service for authentication operations.
 */
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param loginRequest the login request
     * @return the JWT response
     */
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate a JWT token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Get the user details
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Return the JWT response
        return new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getAuthorities().stream()
                        .map(item -> Role.valueOf(item.getAuthority()))
                        .toList()
        );
    }

    /**
     * Registers a new user.
     *
     * @param registerRequest the register request
     * @return true if the user was registered successfully, false otherwise
     */
    public boolean registerUser(RegisterRequest registerRequest) {
        // Check if the username already exists
        if (userDetailsService.existsByUsername(registerRequest.getUsername())) {
            return false;
        }

        // Check if the email already exists
        if (userDetailsService.existsByEmail(registerRequest.getEmail())) {
            return false;
        }

        // Create a new user
        User user = new User(
                registerRequest.getUsername(),
                passwordEncoder.encode(registerRequest.getPassword()),
                registerRequest.getEmail()
        );

        // Set the default role
        user.setRoles(Collections.singletonList(Role.ROLE_USER));

        // Save the user
        userDetailsService.addUser(user);

        return true;
    }
}