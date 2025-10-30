package com.hrm.eureka.common.utils;

import com.hrm.eureka.common.principal.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
    public String extractUsername(String token) {
        return parseToken(token).getSubject();
    }

    public List<String> extractPermissions(String token) {
        Claims claims = parseToken(token);
        Object permissionsObj = claims.get("permissions");
        if (permissionsObj instanceof List<?>) {
            return ((List<?>) permissionsObj).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
