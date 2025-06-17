package com.project.ecom.models;

import com.project.ecom.enums.ApprovalStatus;
import com.project.ecom.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SellerRegistrationApplication extends BaseModel {
    private Long userId;
    private String businessName;
    private String panNumber;
    private String gstRegNumber;
    @Embedded
    private PhoneNumber businessContact;
    private String businessAddress;
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;  // for internal tracking
}
