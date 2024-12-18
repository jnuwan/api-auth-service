package com.microservice.api_auth_service.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final String SEKRETCEY = "yeohRvEEo6f4D22XBuXhPBGn4vq7pMeeg7PsmsSiwHydkToIi5HdF3h75B9Hx3eN";

    public String generateToken(String username) {
    	return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 5 mins
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SEKRETCEY);
        return Keys.hmacShaKeyFor(keyBytes);
	}
    
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
