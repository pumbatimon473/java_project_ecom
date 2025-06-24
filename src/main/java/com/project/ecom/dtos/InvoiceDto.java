package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class InvoiceDto {
    private Long invoiceId;
    private SellerInfo sellerInfo;
    private List<OrderItemDto> orderItems;
    private BigDecimal invoiceTotal;
}
