package com.hrm.eureka.common.filters;

import com.hrm.eureka.common.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);
    private final JwtUtils jwtUtils;

    public JwtRequestFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) {
            log.info("[Common Service] Bearer Token is null");
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = authorizationHeader.substring("Bearer ".length());

        try {
            String username = jwtUtils.extractUsername(jwt);
            List<String> permissions = jwtUtils.extractPermissions(jwt);

            List<GrantedAuthority> authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.error("JWT validation failed: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
