package com.hrm.eureka.security.utils;

import com.hrm.eureka.security.principal.UserPrincipal;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${security.jwt.secret}")
    private String SECRET_KEY;

    @Value("${security.jwt.expiration}")
    private Integer EXPIRED_TIME;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Generate access token
    public String generateToken(UserPrincipal user) {
        System.out.println(user.getUsername());
        System.out.println(user.getAuthorities());
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getRoleName())
                .claim("permissions", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRED_TIME)) // 1 hour
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate token
//    public boolean validateToken(String token, UserDetails userDetails) {
//        try {
//            String username = extractUsername(token);
//            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//        } catch (JwtException | IllegalArgumentException e) {
//            return false; // Token is invalid
//        }
//    }

    // Check if token is expired
//    private boolean isTokenExpired(String token) {
//        Date expirationDate = Jwts.parser()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration();
//        return expirationDate.before(new Date());
//    }

//    public String extractUsername(String token) {
//        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
//    }
}
