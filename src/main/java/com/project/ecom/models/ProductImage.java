package com.project.ecom.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class ProductImage extends BaseModel {
    @OneToOne(mappedBy = "image")
    private Product product;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_image_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;
}
