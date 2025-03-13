package com.project.ecom.repositories;

import com.project.ecom.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressRepository extends JpaRepository<Address, Long> {
}
