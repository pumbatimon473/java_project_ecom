package com.project.ecom.models;

import com.project.ecom.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class User extends BaseModel {
    private String name;
    private String email;
    private String password;

    @Embedded
    private PhoneNumber phoneNumber;

    public abstract UserType getUserType();
}
