package com.project.ecom.dtos;

import com.project.ecom.enums.ApprovalStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSellerRegistrationApplicationDto {
    @NotNull
    private Long applicationId;
    @NotNull
    private ApprovalStatus status;
}
