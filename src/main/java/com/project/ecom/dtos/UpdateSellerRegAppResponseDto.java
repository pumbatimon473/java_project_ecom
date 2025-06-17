package com.project.ecom.dtos;

import com.project.ecom.enums.ApprovalStatus;
import com.project.ecom.models.SellerRegistrationApplication;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSellerRegAppResponseDto {
    private Long applicationId;
    private Long userId;
    private String businessName;
    private ApprovalStatus status;

    public static UpdateSellerRegAppResponseDto from(SellerRegistrationApplication sellerRegApplication) {
        UpdateSellerRegAppResponseDto responseDto = new UpdateSellerRegAppResponseDto();
        responseDto.setApplicationId(sellerRegApplication.getId());
        responseDto.setUserId(sellerRegApplication.getUserId());
        responseDto.setBusinessName(sellerRegApplication.getBusinessName());
        responseDto.setStatus(sellerRegApplication.getApprovalStatus());
        return responseDto;
    }
}
