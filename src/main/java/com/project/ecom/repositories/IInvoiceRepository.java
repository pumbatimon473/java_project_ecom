package com.project.ecom.repositories;

import com.project.ecom.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByOrderId(Long orderId);
}
