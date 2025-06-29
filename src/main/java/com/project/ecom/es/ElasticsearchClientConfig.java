package com.project.ecom.es;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.project.ecom.es.repository")
public class ElasticsearchClientConfig extends ElasticsearchConfiguration {
    @Value("${ECOM_ES_HOST_AND_PORT}")
    private String ES_HOST_AND_PORT;

    @Bean
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(ES_HOST_AND_PORT)
                .build();
    }
}
