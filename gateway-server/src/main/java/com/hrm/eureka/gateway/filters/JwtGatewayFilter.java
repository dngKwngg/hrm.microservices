package com.hrm.eureka.gateway.filters;

import com.hrm.eureka.gateway.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

// Class JwtGatewayFilter is a custom filter for Spring Cloud Gateway that checks for JWT tokens in the Authorization header of incoming requests.
@Component
public class JwtGatewayFilter extends AbstractGatewayFilterFactory<Object> {

    private final JwtUtils jwtUtils;

    public JwtGatewayFilter(JwtUtils jwtUtils) {
        super(Object.class);
        this.jwtUtils = jwtUtils;
    }

    // Return a GatewayFilter, Spring Cloud Gateway will call this method for each request
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            // Get incoming request
            ServerHttpRequest request = exchange.getRequest();

            // Check if the request has an Authorization header
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, "Invalid Authorization Header", HttpStatus.UNAUTHORIZED);
            }

            // Extract the token from the header
            String token = authHeader.substring(7);
            // Validate the token
            if (!jwtUtils.validateToken(token)) {
                return onError(exchange, "Invalid or Expired Token", HttpStatus.UNAUTHORIZED);
            }

            Claims claims = jwtUtils.extractAllClaims(token);
            String username = claims.getSubject();

            List<String> permissions = claims.get("permissions", List.class);

            // Mutating the request to add custom headers
            // Get a mutable builder for the request
            ServerHttpRequest mutatedRequest = request.mutate()
                    // Add custom headers
                    .header("X-Auth-User", username)
                    // Add permissions as a comma-separated string
                    .header("X-Auth-Permissions", String.join(",", permissions))
                    .build();

            // If the token is valid, proceed with the request
            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }
}
