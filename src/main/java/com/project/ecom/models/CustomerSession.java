package com.project.ecom.models;

import com.project.ecom.enums.UserSessionStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class CustomerSession extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private UserSessionStatus status;

    @OneToMany(mappedBy = "session")
    private List<Cart> carts;  // A customer session can have many carts but only one of them will be active

    public Boolean isActive() {
        return this.status == UserSessionStatus.ACTIVE;
    }
}
