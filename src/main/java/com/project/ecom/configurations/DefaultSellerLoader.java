package com.project.ecom.configurations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ecom.dtos.AddAddressRequestDto;
import com.project.ecom.enums.ApprovalStatus;
import com.project.ecom.enums.Country;
import com.project.ecom.exceptions.SimilarRequestException;
import com.project.ecom.models.PhoneNumber;
import com.project.ecom.models.SellerRegistrationApplication;
import com.project.ecom.services.IAdminService;
import com.project.ecom.services.ICustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class DefaultSellerLoader {
    private final ICustomerService customerService;
    private final IAdminService adminService;

    @Autowired
    public DefaultSellerLoader(ICustomerService customerService, IAdminService adminService) {
        this.customerService = customerService;
        this.adminService = adminService;
    }

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void registerSeller() {
        Long adminId = 1L;  // default admin
        String businessName = "Party Kirana Store";
        String panNumber = "UYGHV7656J";
        String gstRegNumber = "7WEUJD3407L9Z5";
        PhoneNumber businessContact = this.getBusinessContact();
        AddAddressRequestDto businessAddress = this.getBusinessAddress();

        try {
            SellerRegistrationApplication sellerRegApplication = customerService.registerAsSeller(adminId, businessName, panNumber, gstRegNumber, businessContact, businessAddress);
            adminService.updateSellerRegApplication(adminId, sellerRegApplication.getId(), ApprovalStatus.APPROVED);
            System.out.println(":: LOG :: DefaultSellerLoader :: SUCCESS");
        } catch (SimilarRequestException e) {
            System.out.println(":: LOG :: DefaultSellerLoader :: The application already exists");
        } catch (JsonProcessingException e) {
            System.out.println(":: LOG :: DefaultSellerLoader :: An error occurred while registering the default seller!");
        } catch (IllegalAccessException e) {
            System.out.println(":: LOG :: DefaultSellerLoader :: The application has already been reviewed.");
        } catch (Exception e) {
            System.out.println(":: LOG :: DefaultSellerLoader :: An unexpected error occurred during registration!");
        }
    }

    private PhoneNumber getBusinessContact() {
        PhoneNumber businessContact = new PhoneNumber();
        businessContact.setCountry(Country.BHARAT);
        businessContact.setPhoneNumber("4681860474");
        return businessContact;
    }

    private AddAddressRequestDto getBusinessAddress() {
        AddAddressRequestDto businessAddress = new AddAddressRequestDto();
        businessAddress.setAddressLine1("Building Number 420, Sector 69, Lutyens' Delhi");
        businessAddress.setCity("Delhi");
        businessAddress.setPinCode("415471");
        businessAddress.setState("Uttar_Pradesh");
        businessAddress.setCountry("Bharat");
        return businessAddress;
    }
}
