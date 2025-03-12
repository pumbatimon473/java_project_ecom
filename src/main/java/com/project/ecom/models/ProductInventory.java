package com.project.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class ProductInventory extends BaseModel {
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
}
