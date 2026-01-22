package com.shop.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    // Khóa bí mật (Trong thực tế nên để trong file properties, nhưng giờ cứ để đây cho chạy được đã)
    private static final String SECRET_KEY = "daylakhobimatcuaduanparkingbookingsystemkhongduocdedautieuhaha";
    
    // Thời gian hết hạn của Token (86400000 ms = 1 ngày)
    private static final long EXPIRATION_TIME = 86400000; 

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Hàm tạo Token từ Username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}