package com.project.ecom.dtos;

import com.project.ecom.enums.UserSessionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerSessionDto {
    private Long sessionId;
    private Long customerId;
    private UserSessionStatus status;
}
