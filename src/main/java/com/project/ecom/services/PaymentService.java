package com.project.ecom.services;

import com.project.ecom.adapters.payment.IPaymentGatewayAdapter;
import com.project.ecom.enums.OrderStatus;
import com.project.ecom.enums.PaymentStatus;
import com.project.ecom.exceptions.*;
import com.project.ecom.models.*;
import com.project.ecom.repositories.ICustomerRepository;
import com.project.ecom.repositories.IOrderRepository;
import com.project.ecom.repositories.IPaymentRepository;
import com.project.ecom.repositories.IProductInventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {
    private final ICustomerRepository customerRepo;
    private final IOrderRepository orderRepo;
    private final IPaymentGatewayAdapter paymentGatewayAdapter;
    private final IPaymentRepository paymentRepo;
    private final IProductInventoryRepository productInventoryRepo;

    @Autowired
    public PaymentService(ICustomerRepository customerRepo, IOrderRepository orderRepo, @Qualifier("stripePaymentGateway") IPaymentGatewayAdapter paymentGatewayAdapter, IPaymentRepository paymentRepo, IProductInventoryRepository productInventoryRepo) {
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.paymentGatewayAdapter = paymentGatewayAdapter;
        this.paymentRepo = paymentRepo;
        this.productInventoryRepo = productInventoryRepo;
    }

    @Override
    public String getPaymentLink(Long customerId, Long orderId) {
        Customer customer = this.customerRepo.findById(customerId)
                .orElseThrow(() -> new UserNotFoundException(customerId));
        Order order = this.orderRepo.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        if (!order.getCustomer().getId().equals(customerId))
            throw new IllegalStateException("The specified order id does not belong to the customer");
        if (order.getStatus() != OrderStatus.PENDING)
            throw new IllegalStateException("Cannot create payment link for the specified order");

        return this.paymentGatewayAdapter.createPaymentLink(order, customer);
    }

    @Override
    @Transactional
    public Payment getPayment(String sessionId) {
        Optional<Payment> paymentOptional = this.paymentRepo.findBySessionId(sessionId);
        if (paymentOptional.isPresent())
            return paymentOptional.get();
        PaymentTransaction paymentTransaction = this.paymentGatewayAdapter.getPaymentTransaction(sessionId);
        Order order = this.orderRepo.findById(paymentTransaction.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(paymentTransaction.getOrderId()));
        if (paymentTransaction.getStatus() == PaymentStatus.FAILURE) {
            order.setStatus(OrderStatus.CANCELLED);
            this.orderRepo.save(order);
        }
        // update the product inventory
        try {
            List<ProductInventory> productInventories = new ArrayList<>();
            for (OrderItem orderItem : order.getOrderItems()) {
                ProductInventory productInventory = this.productInventoryRepo.findByProductId(orderItem.getProduct().getId())
                        .orElseThrow(() -> new ProductNotInInventoryException(orderItem.getProduct().getId()));
                if (productInventory.getQuantity() < orderItem.getQuantity())
                    throw new InsufficientInventoryException(orderItem.getProduct().getId());
                productInventory.setQuantity(productInventory.getQuantity() - orderItem.getQuantity());
                productInventories.add(productInventory);
            }
            this.productInventoryRepo.saveAll(productInventories);
        } catch (ProductNotInInventoryException | InsufficientInventoryException e) {
            throw new OrderConfirmationException(paymentTransaction.getTransactionId());
        }
        // create payment
        Payment payment = new Payment();
        payment.setAmount(paymentTransaction.getAmount());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId(paymentTransaction.getTransactionId());
        payment.setSessionId(sessionId);
        this.paymentRepo.save(payment);
        // update order status
        order.setStatus(OrderStatus.PLACED);
        order.setPayment(payment);
        this.orderRepo.save(order);

        payment.setOrders(List.of(order));
        return payment;
    }
}
