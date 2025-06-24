package com.project.ecom.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class InitializeKafkaTopics {
    @Value("${kafka.topic.order-created}")
    private String orderCreatedTopic;
    @Value("${kafka.topic.order-placed}")
    private String orderPlacedTopic;

    @Bean
    public NewTopic orderCreated() {
        return TopicBuilder.name(this.orderCreatedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderPlaced() {
        return TopicBuilder.name(this.orderPlacedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
