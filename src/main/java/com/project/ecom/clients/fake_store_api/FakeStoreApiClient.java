package com.project.ecom.clients.fake_store_api;

import com.project.ecom.dtos.clients.fake_store_api.FakeStoreProductDto;
import com.project.ecom.exceptions.ExternalServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Component
public class FakeStoreApiClient {
    private final RestTemplate restTemplate;
    private static final String FAKE_STORE_HOST = "https://fakestoreapi.com";

    @Autowired
    public FakeStoreApiClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .connectTimeout(Duration.ofSeconds(3L))
                .readTimeout(Duration.ofSeconds(5L))
                .build();
    }

    public List<FakeStoreProductDto> getAllProducts() {
        String url = FAKE_STORE_HOST + "/products";
        try {
            ResponseEntity<FakeStoreProductDto[]> responseEntity = this.restTemplate.getForEntity(url, FakeStoreProductDto[].class);
            return responseEntity.getBody() != null ? Arrays.asList(responseEntity.getBody()) : List.of();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ExternalServiceException(
                    "FakeStoreApi responded with an error!",
                    e.getStatusCode(),
                    e.getResponseBodyAsString(),
                    e
            );
        }
    }
}
