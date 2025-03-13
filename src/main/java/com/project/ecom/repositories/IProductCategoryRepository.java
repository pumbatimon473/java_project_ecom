package com.project.ecom.repositories;

import com.project.ecom.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
