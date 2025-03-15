package com.project.ecom.repositories;

import com.project.ecom.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
