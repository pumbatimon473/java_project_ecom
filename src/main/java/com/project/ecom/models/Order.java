package com.project.ecom.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.ecom.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "`order`")  // since 'order' is a reserved keyword in my DBs, escaping it with backtick.
public class Order extends BaseModel {
//    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    private Customer customer;

    private Long customerId;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @Embedded
    private OrderTotal orderTotal;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(mappedBy = "order")
    @JsonBackReference
    private Payment payment;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<Invoice> invoices;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    @JsonManagedReference
    private Address deliveryAddress;
}
