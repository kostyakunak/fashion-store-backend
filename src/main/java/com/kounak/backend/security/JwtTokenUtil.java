package com.kounak.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration:86400}")
    private long expiration;
    
    /**
     * Get username from JWT token
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    
    /**
     * Get roles from JWT token
     */
    public String getRolesFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("roles", String.class);
    }

    /**
     * Get expiration date from JWT token
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Get claim from token
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * For retrieving any information from token we will need the secret key
     */
    private Claims getAllClaimsFromToken(String token) {
        Key key = getSigningKey();
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
     * Generate a secure signing key for HS512 algorithm
     * This ensures the key is always at least 512 bits (64 bytes) in length
     */
    private Key getSigningKey() {
        // If secret is 64 bytes or longer, use it directly
        byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (secretBytes.length >= 64) {
            return Keys.hmacShaKeyFor(secretBytes);
        }
        
        // Otherwise, derive a secure key using the secret as a seed
        // This ensures we always have at least 64 bytes (512 bits) for HS512
        byte[] secureKey = new byte[64]; // 512 bits
        
        // Copy the original secret bytes
        System.arraycopy(secretBytes, 0, secureKey, 0, Math.min(secretBytes.length, secureKey.length));
        
        // Fill the rest with a derived pattern to ensure deterministic behavior
        for (int i = secretBytes.length; i < secureKey.length; i++) {
            secureKey[i] = (byte)(secretBytes[i % secretBytes.length] ^ (i * 13));
        }
        
        return Keys.hmacShaKeyFor(secureKey);
    }

    /**
     * Check if the token has expired
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generate token for user
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        
        // Add roles to the token
        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        claims.put("roles", roles);
        
        // Add any additional custom claims here
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * While creating the token:
     * 1. Define claims of the token, like Issuer, Expiration, Subject, ID
     * 2. Sign the JWT using the HS512 algorithm and secret key
     * 3. Compact the JWT to a URL-safe string
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        Key key = getSigningKey();
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Validate token
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}