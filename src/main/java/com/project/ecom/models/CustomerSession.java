package com.project.ecom.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.ecom.enums.UserSessionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
public class CustomerSession extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private UserSessionStatus status;

    @OneToMany(mappedBy = "session")
    @JsonBackReference
    private List<Cart> carts;  // A customer session can have many carts but only one of them will be active

    public Boolean isActive() {
        return this.status == UserSessionStatus.ACTIVE;
    }
}
