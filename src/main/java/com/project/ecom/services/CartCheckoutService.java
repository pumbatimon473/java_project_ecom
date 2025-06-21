package com.project.ecom.services;

import com.project.ecom.clients.AuthClient;
import com.project.ecom.dtos.CartItemDetails;
import com.project.ecom.enums.OrderStatus;
import com.project.ecom.exceptions.*;
import com.project.ecom.models.*;
import com.project.ecom.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartCheckoutService implements ICartCheckoutService {
    private final AuthClient authClient;
    private final IProductRepository productRepo;
    private final IProductInventoryRepository productInventoryRepo;
    private final IAddressRepository addressRepo;
    private final IOrderRepository orderRepo;
    private final IOrderItemRepository orderItemRepo;

    @Autowired
    public CartCheckoutService(AuthClient authClient, IProductRepository productRepo, IProductInventoryRepository productInventoryRepo, IAddressRepository addressRepo, IOrderRepository orderRepo, IOrderItemRepository orderItemRepo) {
        this.authClient = authClient;
        this.productRepo = productRepo;
        this.productInventoryRepo = productInventoryRepo;
        this.addressRepo = addressRepo;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
    }

    @Override
    @Transactional
    public Order checkout(Long customerId, List<CartItemDetails> cartItems, Long deliveryAddressId) {
        this.authClient.getUserInfo(customerId);  // an exception will be thrown if the customerId does not exist
        Address deliveryAddress = verifyDeliveryAddress(customerId, deliveryAddressId);
        List<OrderItem> orderItems = validateCartItems(cartItems);
        OrderTotal orderTotal = calculateOrderTotal(orderItems);
        // create order:
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setDeliveryAddress(deliveryAddress);
        order.setOrderTotal(orderTotal);
        order.setStatus(OrderStatus.PENDING);
        this.orderRepo.save(order);  // order with id
        // bind order with otherItems
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        this.orderItemRepo.saveAll(orderItems);
        order.setOrderItems(orderItems);
        return order;
    }

    private Address verifyDeliveryAddress(Long customerId, Long deliveryAddressId) {
        Address deliveryAddress = this.addressRepo.findById(deliveryAddressId)
                .orElseThrow(() -> new AddressNotFoundException(deliveryAddressId));
        if (!deliveryAddress.getUserId().equals(customerId))
            throw new AddressNotLinkedException(deliveryAddressId, customerId);
        return deliveryAddress;
    }

    private List<OrderItem> validateCartItems(List<CartItemDetails> cartItems) {
        List<OrderItem> orderItems = new ArrayList<>();
        // BigDecimal orderAmount = BigDecimal.valueOf(0);
        for (CartItemDetails cartItem : cartItems) {
            // check product
            Product product = this.productRepo.findById(cartItem.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(cartItem.getProductId()));
            // check product inventory
            ProductInventory productInventory = this.productInventoryRepo.findByProductId(cartItem.getProductId())
                    .orElseThrow(() -> new ProductNotInInventoryException(cartItem.getProductId()));
            if (productInventory.getQuantity() < cartItem.getQuantity())
                throw new InsufficientInventoryException(cartItem.getProductId());

            // create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);

            // orderAmount = orderAmount.add(cartItem.getPriceAtAddition().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }
        return orderItems;
    }

    private OrderTotal calculateOrderTotal(List<OrderItem> orderItems) {
        BigDecimal orderAmount = BigDecimal.valueOf(0);
        for (OrderItem orderItem : orderItems) {
            orderAmount = orderAmount.add(orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }
        OrderTotal orderTotal = new OrderTotal();
        orderTotal.setAmount(orderAmount);
        return orderTotal;
    }
}
