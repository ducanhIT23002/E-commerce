package com.shop.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final String SECRET_KEY = "daylakhobimatcuaduanparkingbookingsystemkhongduocdedautieuhaha";
    private static final long EXPIRATION_TIME = 86400000; 

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) 
                .setIssuedAt(new Date()) 
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) 
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) 
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true; 
        } catch (Exception e) {
            System.err.println("Lỗi Token: " + e.getMessage());
        }
        return false; 
    }
}