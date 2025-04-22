package com.hrm.eureka.user.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Value("${security.jwt.secret}")
    private String SECRET_KEY;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);

                // Parse the token
                Claims claims = Jwts.parser()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();

                // Check both "role" and "roles" keys to be safe
                // Only claim "roles" key
                List<String> permissions = claims.get("permissions", List.class);

                if (permissions != null) {
                    System.out.println("Extracted Roles: " + permissions); // Debugging

                    // Convert roles to authorities with ROLE_ prefix
                    List<GrantedAuthority> authorities = permissions.stream()
                            .map(permission -> new SimpleGrantedAuthority(permission))
                            .collect(Collectors.toList());

                    // Also add the original role as an authority for hasAuthority checks
//                    permisisons.forEach(permisison -> authorities.add(new SimpleGrantedAuthority(permisison)));

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                System.err.println("JWT Authentication failed: " + e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }

        // Always continue the filter chain
        filterChain.doFilter(request, response);
    }
}
