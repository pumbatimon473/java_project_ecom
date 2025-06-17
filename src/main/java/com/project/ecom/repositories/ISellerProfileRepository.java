package com.project.ecom.repositories;

import com.project.ecom.models.SellerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISellerProfileRepository extends JpaRepository<SellerProfile, Long> {
    Optional<SellerProfile> findBySellerId(Long sellerId);
}
