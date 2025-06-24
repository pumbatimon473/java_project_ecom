package com.project.ecom.kafka.producers;

import com.project.ecom.kafka.events.OrderPlacedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderPlacedEventProducer {
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    @Value("${kafka.topic.order-placed}")
    private String ORDER_PLACED_EVENTS_TOPIC;

    @Autowired
    public OrderPlacedEventProducer(KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(OrderPlacedEvent event) {
        this.kafkaTemplate.send(ORDER_PLACED_EVENTS_TOPIC, event);
    }
}
