package com.project.ecom.repositories;

import com.project.ecom.enums.AddressType;
import com.project.ecom.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long customerId);

    List<Address> findByUserIdAndAddressType(Long userId, AddressType addressType);
}
