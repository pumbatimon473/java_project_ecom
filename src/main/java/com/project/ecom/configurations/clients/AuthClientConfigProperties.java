package com.project.ecom.configurations.clients;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "ecom.auth-service")
public class AuthClientConfigProperties {
    private String url;
}
