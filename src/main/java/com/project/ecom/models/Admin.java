package com.project.ecom.models;

import com.project.ecom.enums.UserType;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Deprecated
public class Admin extends User {
    @Override
    public UserType getUserType() {
        return UserType.ADMIN;
    }
}
