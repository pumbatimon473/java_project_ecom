package com.project.ecom.dtos;

import com.project.ecom.dtos.clients.auth_client.UserInfoDto;
import com.project.ecom.models.Address;
import com.project.ecom.models.CustomerProfile;
import com.project.ecom.models.PhoneNumber;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CustomerProfileResponseDto {
    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private PhoneNumber phoneNumber;
    private List<AddAddressResponseDto> addresses;

    public static CustomerProfileResponseDto from(UserInfoDto customerInfo, CustomerProfile customerProfile, List<Address> addresses) {
        CustomerProfileResponseDtoBuilder profileBuilder = CustomerProfileResponseDto.builder()
                .customerId(customerInfo.getUserId())
                .firstName(customerInfo.getFirstName())
                .lastName(customerInfo.getLastName())
                .email(customerInfo.getEmail())
                .addresses(addresses.stream().map(AddAddressResponseDto::from).toList());

        if (customerProfile != null) {
            profileBuilder.phoneNumber(customerProfile.getPhoneNumber());
        }

        return profileBuilder.build();
    }
}
