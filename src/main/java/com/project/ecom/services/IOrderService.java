package com.project.ecom.services;

import com.project.ecom.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    Order createOrder(Long customerId, List<Long> cartItemIds, Long deliveryAddressId);  // places order from the cart

    List<Order> getActiveOrders(Long customerId);  // returns the list of recent orders (not delivered yet)

    Page<Order> getOrders(Long customerId, Pageable pageable);

    Order getOrder(Long customerId, Long orderId);
}
