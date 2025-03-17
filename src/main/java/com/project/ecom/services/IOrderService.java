package com.project.ecom.services;

import com.project.ecom.models.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(Long customerId, List<Long> cartItemIds, Long deliveryAddressId);  // places order from the cart
}
