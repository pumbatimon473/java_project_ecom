package com.project.ecom.dtos.clients.auth_client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
}
