package com.project.ecom.services;

import com.project.ecom.dtos.CartItemDetails;
import com.project.ecom.models.Order;

import java.util.List;

public interface ICartCheckoutService {
    Order checkout(Long customerId, List<CartItemDetails> cartItems, Long deliveryAddressId);
}
