package com.project.ecom.models;

import com.project.ecom.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Deprecated
public abstract class User {
    private Long id;
    private String name;
    private String email;
    private String password;

    public abstract UserType getUserType();
}
