package com.project.ecom.kafka.consumers;

import com.project.ecom.kafka.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedEventListener {
    private static final Logger logger = LoggerFactory.getLogger(OrderCreatedEventListener.class);

    @KafkaListener(topics = "${kafka.topic.order-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        logger.info(":: CONSUMED EVENT PUBLISHED AT KAFKA TOPIC :: ORDER-CREATED-EVENTS\n{}", event);
    }
}
