package com.project.ecom.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Product extends BaseModel {
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    private String description;
    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "image_id")
    private ProductImage image;

    @OneToOne(mappedBy = "product")
    private ProductInventory inventory;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
