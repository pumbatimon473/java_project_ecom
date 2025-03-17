package com.project.ecom.repositories;

import com.project.ecom.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    @Query(value = "select case when count(*) > 0 then true else false end from customer_address where customer_id = :customerId and address_id = :addressId", nativeQuery = true)
    Long isAddressAssociatedWithCustomer(@Param("addressId") Long addressId, @Param("customerId") Long customerId);
}
