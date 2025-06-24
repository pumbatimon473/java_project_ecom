package com.project.ecom.repositories;

import com.project.ecom.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findBySessionId(String sessionId);

    Optional<Payment> findByOrderId(Long orderId);
}
