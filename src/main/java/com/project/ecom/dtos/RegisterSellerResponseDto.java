package com.project.ecom.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.ecom.enums.ApprovalStatus;
import com.project.ecom.models.PhoneNumber;
import com.project.ecom.models.SellerRegistrationApplication;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class RegisterSellerResponseDto {
    private Long applicationId;
    private Long userId;
    private String businessName;
    private String panNumber;
    private String gstRegNumber;
    private PhoneNumber businessContact;
    //private JsonObject businessAddress;
    private Map<String, Object> businessAddress;
    private ApprovalStatus approvalStatus;


    public static RegisterSellerResponseDto from(SellerRegistrationApplication regApplication) throws JsonProcessingException {
        return RegisterSellerResponseDto.builder()
                .applicationId(regApplication.getId())
                .userId(regApplication.getUserId())
                .businessName(regApplication.getBusinessName())
                .panNumber(regApplication.getPanNumber())
                .gstRegNumber(regApplication.getGstRegNumber())
                .businessContact(regApplication.getBusinessContact())
                .businessAddress(
                        //new Gson().fromJson(regApplication.getBusinessAddress(), JsonObject.class)
                        new ObjectMapper().readValue(regApplication.getBusinessAddress(), Map.class)
                )
                .approvalStatus(regApplication.getApprovalStatus())
                .build();

    }
}
