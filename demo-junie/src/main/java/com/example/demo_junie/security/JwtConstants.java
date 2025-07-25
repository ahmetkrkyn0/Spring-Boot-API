package com.example.demo_junie.security;

/**
 * Constants for JWT authentication.
 */
public class JwtConstants {
    
    // Secret key for signing JWT tokens
    public static final String JWT_SECRET = "demoJunieSecretKey2025JwtAuthenticationSecureRandomKeyForSigningTokens";
    
    // Token expiration time in milliseconds (24 hours)
    public static final long JWT_EXPIRATION_MS = 86400000;
    
    // Token prefix in Authorization header
    public static final String TOKEN_PREFIX = "Bearer ";
    
    // Header name for Authorization
    public static final String HEADER_STRING = "Authorization";
    
    // Private constructor to prevent instantiation
    private JwtConstants() {
        throw new IllegalStateException("Utility class");
    }
}