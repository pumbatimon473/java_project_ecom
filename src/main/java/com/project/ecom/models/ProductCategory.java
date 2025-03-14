package com.project.ecom.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore  // tells Jackson's serializer to ignore serialization of the products collection
    private List<Product> products;
}
