package com.project.ecom.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ecom.enums.ApprovalStatus;
import com.project.ecom.exceptions.UnauthorizedUserException;
import com.project.ecom.exceptions.UserAlreadyExistsException;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.*;

public interface IAdminService {
    ProductCategory addProductCategory(Long adminId, String name, String description);

    SellerRegistrationApplication updateSellerRegApplication(Long adminId, Long applicationId, ApprovalStatus status) throws JsonProcessingException, IllegalAccessException;
}
