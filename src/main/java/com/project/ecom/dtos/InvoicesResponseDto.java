package com.project.ecom.dtos;

import com.project.ecom.enums.OrderStatus;
import com.project.ecom.models.Invoice;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class InvoicesResponseDto {
    private Long orderId;
    private Long customerId;
    private OrderStatus orderStatus;
    private String transactionId;
    private List<InvoiceDto> invoices;
    private BigDecimal orderTotal;

    public static InvoicesResponseDto from(InvoiceDetails invoiceDetails) {
        InvoicesResponseDto responseDto = new InvoicesResponseDto();
        responseDto.setOrderId(invoiceDetails.getOrder().getId());
        responseDto.setCustomerId(invoiceDetails.getOrder().getCustomerId());
        responseDto.setOrderStatus(invoiceDetails.getOrder().getStatus());
        responseDto.setTransactionId(invoiceDetails.getPayment().getTransactionId());
        responseDto.setInvoices(invoiceDetails.getInvoices());
        responseDto.setOrderTotal(invoiceDetails.getOrder().getOrderTotal().getOrderTotal());

        return responseDto;
    }
}
