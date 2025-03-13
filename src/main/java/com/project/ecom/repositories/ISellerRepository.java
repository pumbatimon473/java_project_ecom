package com.project.ecom.repositories;

import com.project.ecom.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByEmail(String email);
}
