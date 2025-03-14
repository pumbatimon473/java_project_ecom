package com.project.ecom.repositories;

import com.project.ecom.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductImageRepository extends JpaRepository<ProductImage, Long> {
}
