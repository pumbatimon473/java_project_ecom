package com.project.ecom.repositories;

import com.project.ecom.enums.UserSessionStatus;
import com.project.ecom.models.CustomerSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICustomerSessionRepository extends JpaRepository<CustomerSession, Long> {
    Optional<CustomerSession> findByCustomerIdAndStatus(Long customerId, UserSessionStatus status);
}
