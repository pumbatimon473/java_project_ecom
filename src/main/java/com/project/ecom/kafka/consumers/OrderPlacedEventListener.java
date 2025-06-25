package com.project.ecom.kafka.consumers;

import com.project.ecom.kafka.events.OrderPlacedEvent;
import com.project.ecom.models.Invoice;
import com.project.ecom.services.IInvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderPlacedEventListener {
    private static final Logger logger = LoggerFactory.getLogger(OrderPlacedEventListener.class);
    private final IInvoiceService invoiceService;

    @Autowired
    public OrderPlacedEventListener(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Async  // As the invoice generation is happening synchronously (Blocking Call)
    @KafkaListener(topics = "${kafka.topic.order-placed}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleOrderPlacedEvent(OrderPlacedEvent event) {
        logger.info(":: PAYMENT CONFIRMED for the order ::\n{}", event);
        List<Invoice> invoices = this.invoiceService.generateInvoices(event.getOrderId());
        logger.info(":: GENERATED INVOICES ::\nOrder id {} | Invoice Ids {}", event.getOrderId(), invoices.stream().map(Invoice::getId).toList());
    }
}
