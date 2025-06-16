package com.project.ecom.services;

import com.project.ecom.enums.CartStatus;
import com.project.ecom.enums.OrderStatus;
import com.project.ecom.exceptions.*;
import com.project.ecom.models.*;
import com.project.ecom.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    private final ICartItemRepository cartItemRepo;
    private final IAddressRepository addressRepo;
    private final IProductInventoryRepository productInventoryRepo;
    private final IOrderRepository orderRepo;
    private final IOrderItemRepository orderItemRepo;

    @Autowired
    public OrderService(ICartItemRepository cartItemRepo, IAddressRepository addressRepo, IProductInventoryRepository productInventoryRepo, IOrderRepository orderRepo, IOrderItemRepository orderItemRepo) {
        this.cartItemRepo = cartItemRepo;
        this.addressRepo = addressRepo;
        this.productInventoryRepo = productInventoryRepo;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
    }

    @Override
    public Order createOrder(Long customerId, List<Long> cartItemIds, Long deliveryAddressId) {
        Address deliveryAddress = this.addressRepo.findById(deliveryAddressId)
                .orElseThrow(() -> new AddressNotFoundException(deliveryAddressId));
        // validate cart items
        List<CartItem> cartItems = new ArrayList<>();
        BigDecimal orderAmount = BigDecimal.valueOf(0);
        for (Long cartItemId : cartItemIds) {
            CartItem cartItem = this.cartItemRepo.findById(cartItemId)
                    .orElseThrow(() -> new CartItemNotFoundException(cartItemId));
            if (cartItem.getCart().getStatus() != CartStatus.ACTIVE)
                throw new NoActiveCartLinkedException(cartItemId);
            if (!cartItem.getCart().getSession().getCustomerId().equals(customerId))
                throw new IllegalStateException(String.format("Cart item with id %d is not linked to the customer with id %d", cartItemId, customerId));
            // check product inventory
            ProductInventory productInventory = this.productInventoryRepo.findByProductId(cartItem.getProduct().getId())
                    .orElseThrow(() -> new ProductNotInInventoryException(cartItem.getProduct().getId()));
            if (productInventory.getQuantity() < cartItem.getQuantity())
                throw new InsufficientInventoryException(cartItem.getProduct().getId());
            cartItems.add(cartItem);

            orderAmount = orderAmount.add(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }
        // - order total
        OrderTotal orderTotal = new OrderTotal();
        orderTotal.setAmount(orderAmount);
        // create order:
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setDeliveryAddress(deliveryAddress);
        order.setOrderTotal(orderTotal);
        order.setStatus(OrderStatus.PENDING);
        this.orderRepo.save(order);  // order with id
        // - create order items
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            return orderItem;
        }).collect(Collectors.toList());  // mutable list of order items - required for saving them to the repo so that they can have their ids
        this.orderItemRepo.saveAll(orderItems);
        order.setOrderItems(orderItems);
        return order;
    }

    @Override
    public List<Order> getActiveOrders(Long customerId) {
        List<OrderStatus> excludedStatuses = List.of(OrderStatus.DELIVERED, OrderStatus.CANCELLED);
        List<Order> activeOrders = this.orderRepo.getActiveOrders(customerId, excludedStatuses);
        if (activeOrders.isEmpty())
            throw new NoRecentOrdersException(customerId);
        return activeOrders;
    }

    @Override
    public Page<Order> getOrders(Long customerId, Pageable pageable) {
        return this.orderRepo.findByCustomerId(customerId, pageable);
    }

    @Override
    public Order getOrder(Long customerId, Long orderId) {
        return this.orderRepo.findByIdAndCustomerId(orderId, customerId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
