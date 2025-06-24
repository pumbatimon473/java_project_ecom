package com.project.ecom.kafka.producers;

import com.project.ecom.kafka.events.OrderCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderCreatedEventProducer {
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    @Value("${kafka.topic.order-created}")
    private String ORDER_CREATED_EVENTS_TOPIC;

    @Autowired
    public OrderCreatedEventProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(OrderCreatedEvent event) {
        this.kafkaTemplate.send(ORDER_CREATED_EVENTS_TOPIC, event);
    }
}
