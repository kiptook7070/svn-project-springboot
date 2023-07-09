package com.microservice.authentication.auth_service.utils.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {
    private static final String SECRET_KEY="e1hqJYKbr2DzzLJ96XiDi5G/u6pCdmXjx9B1ipL7znnx+5iigDeRe3CWe/gi4YE6tEPu7FBWPBun2hCuHRS934Pa++VwCa9t1mG+hGc987uA8r+wcYdOBYMwLrbfxzSmCZoRmLvKQua1yqw5hRzEbA/r+LzGwcQ2HdZRaoK0+kd03ZefKjixsE3KGODUCTCytGZFEzQcbfdmJS8skUaTrovNjm3Pz8zSxvKprMCn8990I7go3oX/ePGuJNa3/4EyHhd7YUNun5rlwKWlBxbWryhtEB8V+c2jottY+7SzQo1ce4x2KF77eCrldi/kMh0VSBTNHz+MFY0Ncoxk0kMnqg6oubnrMNMMK8rBsD0vvvo=";
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    private Claims extractAllClaims(String token){

        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return  (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
}

    public String generateToken(
        Map<String, Object> extractClaims,
        UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
