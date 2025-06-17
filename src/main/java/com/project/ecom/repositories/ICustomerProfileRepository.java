package com.project.ecom.repositories;

import com.project.ecom.models.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {
    Optional<CustomerProfile> findByCustomerId(Long customerId);
}
