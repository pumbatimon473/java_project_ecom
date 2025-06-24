package com.project.ecom.services;

import com.project.ecom.dtos.InvoiceDetails;
import com.project.ecom.dtos.InvoiceDto;
import com.project.ecom.dtos.OrderItemDto;
import com.project.ecom.dtos.SellerInfo;
import com.project.ecom.enums.OrderStatus;
import com.project.ecom.exceptions.OrderNotFoundException;
import com.project.ecom.exceptions.PaymentDetailsNotFoundException;
import com.project.ecom.exceptions.SellerProfileNotFoundException;
import com.project.ecom.models.*;
import com.project.ecom.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class InvoiceService implements IInvoiceService {
    private final IInvoiceRepository invoiceRepo;
    private final IOrderRepository orderRepo;
    private final IOrderItemRepository orderItemRepo;
    private final ISellerProfileRepository sellerProfileRepo;
    private final IPaymentRepository paymentRepo;

    @Autowired
    public InvoiceService(IInvoiceRepository invoiceRepo, IOrderRepository orderRepo, IOrderItemRepository orderItemRepo, ISellerProfileRepository sellerProfileRepo, IPaymentRepository paymentRepo) {
        this.invoiceRepo = invoiceRepo;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.sellerProfileRepo = sellerProfileRepo;
        this.paymentRepo = paymentRepo;
    }

    @Override
    @Transactional  // Avoids LazyInitializationException
    public List<Invoice> generateInvoices(Long orderId) {
        Order order = this.orderRepo.findByIdAndStatus(orderId, OrderStatus.PLACED)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order id or order status"));
        // group order items by their sellers
        Map<Long, List<OrderItem>> groupedOrderItems = new HashMap<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            Long sellerId = orderItem.getProduct().getSellerId();
            if (!groupedOrderItems.containsKey(sellerId))
                groupedOrderItems.put(sellerId, new ArrayList<>());
            groupedOrderItems.get(sellerId).add(orderItem);
        }
        // generate invoices for each seller
        List<Invoice> invoices = new ArrayList<>();
        for (Long sellerId : groupedOrderItems.keySet()) {
            Invoice invoice = new Invoice();
            invoice.setOrder(order);
            invoice.setOrderItems(groupedOrderItems.get(sellerId));
            invoice.setSellerId(sellerId);
            invoice.setPayment(order.getPayment());
            invoices.add(invoice);
        }
        invoices = this.invoiceRepo.saveAll(invoices);

        // establishing bidirectional relationship between OrderItem and Invoice
        for (Invoice invoice : invoices) {
            for (OrderItem orderItem : invoice.getOrderItems()) {
                orderItem.setInvoice(invoice);  // setting invoice with id
                orderItemRepo.save(orderItem);
            }
        }

        return invoices;
    }

    @Override
    public InvoiceDetails getInvoices(Long customerId, Long orderId) {
        Order order = this.orderRepo.findByIdAndCustomerId(orderId, customerId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        if (order.getStatus() == OrderStatus.PENDING)
            throw new IllegalStateException("Order not confirmed!");

        Payment payment = this.paymentRepo.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentDetailsNotFoundException("No payment info found for order id: " + orderId));

        List<Invoice> invoices = this.invoiceRepo.findByOrderId(orderId);
        List<InvoiceDto> invoiceDtos = convertToInvoiceDtos(invoices);

        InvoiceDetails invoiceDetails = new InvoiceDetails();
        invoiceDetails.setOrder(order);
        invoiceDetails.setPayment(payment);
        invoiceDetails.setInvoices(invoiceDtos);
        return invoiceDetails;
    }

    private List<InvoiceDto> convertToInvoiceDtos(List<Invoice> invoices) {
        List<InvoiceDto> invoiceDtos = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceDto invoiceDto = new InvoiceDto();
            invoiceDto.setInvoiceId(invoice.getId());

            SellerProfile sellerProfile = this.sellerProfileRepo.findBySellerId(invoice.getSellerId())
                    .orElseThrow(() -> new SellerProfileNotFoundException(invoice.getSellerId()));
            SellerInfo sellerInfo = getSellerInfo(sellerProfile);
            invoiceDto.setSellerInfo(sellerInfo);

            invoiceDto.setOrderItems(invoice.getOrderItems().stream().map(OrderItemDto::from).toList());

            BigDecimal totalAmount = BigDecimal.valueOf(0);
            for (OrderItemDto orderItemDto : invoiceDto.getOrderItems()) {
                totalAmount = totalAmount.add(orderItemDto.getProduct().getPrice().multiply(BigDecimal.valueOf(orderItemDto.getQuantity())));
            }
            invoiceDto.setInvoiceTotal(totalAmount);

            invoiceDtos.add(invoiceDto);
        }
        return invoiceDtos;
    }

    private static SellerInfo getSellerInfo(SellerProfile sellerProfile) {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(sellerProfile.getSellerId());
        sellerInfo.setBusinessName(sellerProfile.getBusinessName());
        sellerInfo.setBusinessContact(sellerProfile.getBusinessContact());
        return sellerInfo;
    }
}
