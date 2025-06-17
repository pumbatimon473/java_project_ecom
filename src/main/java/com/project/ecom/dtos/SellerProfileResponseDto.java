package com.project.ecom.dtos;

import com.project.ecom.dtos.clients.auth_client.UserInfoDto;
import com.project.ecom.models.Address;
import com.project.ecom.models.PhoneNumber;
import com.project.ecom.models.SellerProfile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Builder
public class SellerProfileResponseDto {
    private Long sellerId;
    private String firstName;
    private String lastName;
    private String email;
    private String businessName;
    private String panNumber;
    private String gstRegNumber;
    private PhoneNumber businessContact;
    private AddAddressResponseDto businessAddress;

    public static SellerProfileResponseDto from(UserInfoDto userInfo, SellerProfile sellerProfile, List<Address> addresses) {
        SellerProfileResponseDtoBuilder profileBuilder = SellerProfileResponseDto.builder()
                .sellerId(userInfo.getUserId())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .email(userInfo.getEmail());

        addresses.stream().findFirst().ifPresent(addr -> {
            profileBuilder.businessAddress(AddAddressResponseDto.from(addr));
        });

        if (sellerProfile != null) {
            profileBuilder.businessName(sellerProfile.getBusinessName())
                    .panNumber(sellerProfile.getPanNumber())
                    .gstRegNumber(sellerProfile.getGstRegNumber())
                    .businessContact(sellerProfile.getBusinessContact());
        }

        return profileBuilder.build();
    }
}
