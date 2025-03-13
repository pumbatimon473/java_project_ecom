package com.project.ecom.models;

import com.project.ecom.enums.UserType;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Admin extends User {
    @Override
    public UserType getUserType() {
        return UserType.ADMIN;
    }
}
