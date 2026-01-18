package com.example.demo.security;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final String SECRET =
            "Inventory-super-secure-secret-key-123456";

    private final Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    private static final long EXPIRATION_TIME = 86400000; // 1 day

    // -----------------------
    // GENERATE TOKEN WITH CLEAN ROLE (NO ROLE_ PREFIX)
    // -----------------------
    public String generateToken(String username, String role) {

        // Defensive cleanup in case role accidentally has ROLE_ prefix
        String cleanRole = role.startsWith("ROLE_")
                ? role.substring(5)
                : role;

        return Jwts.builder()
                .setClaims(Map.of("role", cleanRole))
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // -----------------------
    // EXTRACT USERNAME
    // -----------------------
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // -----------------------
    // EXTRACT ROLE (RETURNS ADMIN / EMPLOYEE)
    // -----------------------
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // -----------------------
    // INTERNAL CLAIM PARSER
    // -----------------------
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
