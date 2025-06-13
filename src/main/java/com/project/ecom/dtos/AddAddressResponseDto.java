package com.project.ecom.dtos;

import com.project.ecom.enums.Country;
import com.project.ecom.models.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAddressResponseDto {
    private Long id;
    private Long userId;
    private String addressLine1;
    private String addressLine2;
    private String pinCode;
    private String landmark;
    private String city;
    private String state;
    private Country country;

    public static AddAddressResponseDto from(Address address) {
        AddAddressResponseDto responseDto = new AddAddressResponseDto();
        responseDto.setId(address.getId());
        responseDto.setUserId(address.getUserId());
        responseDto.setAddressLine1(address.getAddressLine1());
        responseDto.setAddressLine2(address.getAddressLine2());
        responseDto.setLandmark(address.getLandmark());
        responseDto.setPinCode(address.getPinCode());
        responseDto.setCity(address.getCity());
        responseDto.setState(address.getState());
        responseDto.setCountry(address.getCountry());
        return responseDto;
    }
}
