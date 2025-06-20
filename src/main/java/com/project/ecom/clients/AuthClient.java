package com.project.ecom.clients;

import com.project.ecom.dtos.clients.auth_client.UserInfoDto;
import com.project.ecom.exceptions.ExternalServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthClient {
    private final RestTemplate restTemplate;
    @Value("${ecom.auth-service.url}")
    private String authServiceHost;

    @Autowired
    public AuthClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public UserInfoDto getUserInfo(Long userId) {
        String url = new StringBuilder(this.authServiceHost)
                .append("/api/users/basic-info/{id}")
                .toString();
        try {
            ResponseEntity<UserInfoDto> responseEntity = restTemplate.getForEntity(url, UserInfoDto.class, userId);
            return responseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ExternalServiceException(
                    "AuthService responded with an error!",
                    e.getStatusCode(),
                    e.getResponseBodyAsString(),
                    e
            );
        }
    }

}
