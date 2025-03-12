package com.project.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class ProductCategory extends BaseModel {
    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
