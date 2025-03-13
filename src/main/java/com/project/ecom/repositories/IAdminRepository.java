package com.project.ecom.repositories;

import com.project.ecom.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
}
