package com.hrm.eureka.gateway.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.jwt")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtProperties {
    private String secret;
    private Integer expiration;
}
