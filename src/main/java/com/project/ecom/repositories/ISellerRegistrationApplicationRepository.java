package com.project.ecom.repositories;

import com.project.ecom.models.SellerRegistrationApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISellerRegistrationApplicationRepository extends JpaRepository<SellerRegistrationApplication, Long> {
    Optional<SellerRegistrationApplication> findByUserIdAndBusinessNameIgnoreCase(Long userId, String trim);
}
