package com.project.ecom.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class ProductImage extends BaseModel {
    @OneToOne(mappedBy = "image")
    @JsonBackReference  // Manages circular references during JSON serialization - Inverse Relation
    private Product product;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_image_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;
}
