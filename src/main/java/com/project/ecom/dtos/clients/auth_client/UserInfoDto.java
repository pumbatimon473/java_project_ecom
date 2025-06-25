package com.project.ecom.dtos.clients.auth_client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfoDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
}
