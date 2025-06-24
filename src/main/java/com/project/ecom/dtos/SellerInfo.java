package com.project.ecom.dtos;

import com.project.ecom.models.PhoneNumber;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerInfo {
    private Long sellerId;
    private String businessName;
    private PhoneNumber businessContact;
}
