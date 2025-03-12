package com.project.ecom.models;

import com.project.ecom.enums.CartStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Cart extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "session_id")
    private CustomerSession session;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;

    @Enumerated(EnumType.STRING)
    private CartStatus status;
}
