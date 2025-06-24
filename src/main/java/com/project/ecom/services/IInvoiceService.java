package com.project.ecom.services;

import com.project.ecom.dtos.InvoiceDetails;
import com.project.ecom.models.Invoice;

import java.util.List;

public interface IInvoiceService {
    List<Invoice> generateInvoices(Long orderId);

    InvoiceDetails getInvoices(Long customerId, Long orderId);
}
