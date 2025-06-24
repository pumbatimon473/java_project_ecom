package com.project.ecom.dtos;

import com.project.ecom.enums.OrderStatus;
import com.project.ecom.models.Order;
import com.project.ecom.models.Payment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InvoiceDetails {
    private Order order;
    private Payment payment;
    private List<InvoiceDto> invoices;
}
