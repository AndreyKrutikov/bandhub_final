package by.krutikov.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("jwt")
public class JwtTokenConfig {
    private String secret;
    private Integer expiration;
}
