package com.project.ecom.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Invoice extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "invoice")
    @JsonBackReference
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @JsonManagedReference
    private Payment payment;

//    @ManyToOne
//    @JoinColumn(name = "seller_id")
//    private Seller seller;

    private Long sellerId;
}
