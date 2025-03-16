package com.project.ecom.repositories;

import com.project.ecom.enums.CartStatus;
import com.project.ecom.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findBySessionIdAndStatus(Long sessionId, CartStatus cartStatus);
}
