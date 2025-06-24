package com.project.ecom.controllers;

import com.project.ecom.controllers.reusables.Reusable;
import com.project.ecom.dtos.*;
import com.project.ecom.models.Invoice;
import com.project.ecom.models.Order;
import com.project.ecom.services.IInvoiceService;
import com.project.ecom.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final IOrderService orderService;
    private final IInvoiceService invoiceService;

    @Autowired
    public OrderController(IOrderService orderService, IInvoiceService invoiceService) {
        this.orderService = orderService;
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getRecentOrders(@AuthenticationPrincipal Jwt jwt) {
        // Method 1: Reading claim from the Jwt using @AuthenticationPrincipal
        Long customerId = jwt.getClaim("user_id");
        List<Order> orders = this.orderService.getActiveOrders(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(orders.stream().map(OrderDto::from).toList());
    }

    @GetMapping("/history")
    public ResponseEntity<GetOrdersResponseDto> getOrderHistory(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
            ) {
        // Method 2: Reading claim from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Long customerId = jwt.getClaim("user_id");
        Page<Order> page = this.orderService.getOrders(customerId, pageable);
        return ResponseEntity.ok(Reusable.mapPageToGetOrdersResponseDto(page));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsDto> getOrder(@PathVariable(name = "orderId") Long orderId, Authentication authentication) {
        // Method 3: Reading claim by directly injecting Authentication
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Long customerId = jwt.getClaim("user_id");
        Order order = this.orderService.getOrder(customerId, orderId);
        return ResponseEntity.ok(OrderDetailsDto.from(order));
    }

    @GetMapping("/{orderId}/invoices")
    public ResponseEntity<InvoicesResponseDto> getInvoices(@PathVariable(name = "orderId") Long orderId, @AuthenticationPrincipal Jwt jwt) {
        Long customerId = jwt.getClaim("user_id");
        InvoiceDetails invoiceDetails = this.invoiceService.getInvoices(customerId, orderId);
        return ResponseEntity.ok(InvoicesResponseDto.from(invoiceDetails));
    }
}
