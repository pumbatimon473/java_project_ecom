package com.project.ecom.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.ecom.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "`order`")  // since 'order' is a reserved keyword in my DBs, escaping it with backtick.
public class Order extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @Embedded
    private OrderTotal orderTotal;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne(mappedBy = "order")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    @JsonManagedReference
    private Address deliveryAddress;
}
