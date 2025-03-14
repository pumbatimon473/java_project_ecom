package com.project.ecom.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference  // Manages circular references during JSON serialization - Forward relation
    private ProductImage image;

    @OneToOne(mappedBy = "product")
    @JsonIgnore
    private ProductInventory inventory;

    @OneToMany(mappedBy = "product")
    @JsonIgnore  // ignores serialization of the cartItems collection
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "product")
    @JsonIgnore  // ignores serialization of the orderItems collection
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
