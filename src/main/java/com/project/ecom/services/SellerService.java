package com.project.ecom.services;

import com.project.ecom.clients.AuthClient;
import com.project.ecom.dtos.SellerProfileResponseDto;
import com.project.ecom.dtos.clients.auth_client.UserInfoDto;
import com.project.ecom.enums.UserType;
import com.project.ecom.exceptions.ProductCategoryNotFoundException;
import com.project.ecom.exceptions.ProductNotFoundException;
import com.project.ecom.exceptions.UnauthorizedUserException;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.*;
import com.project.ecom.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SellerService implements ISellerService {
    private final IProductRepository productRepo;
    private final IProductInventoryRepository productInventoryRepo;
    private final IProductCategoryRepository productCategoryRepo;
    private final IProductImageRepository productImageRepo;
    private final ISellerProfileRepository sellerProfileRepo;
    private final AuthClient authClient;
    private final IAddressRepository addressRepo;

    @Autowired
    public SellerService(IProductRepository productRepo, IProductInventoryRepository productInventoryRepo,
                         IProductCategoryRepository productCategoryRepo, IProductImageRepository productImageRepo, ISellerProfileRepository sellerProfileRepo, AuthClient authClient, IAddressRepository addressRepo) {
        this.productRepo = productRepo;
        this.productInventoryRepo = productInventoryRepo;
        this.productCategoryRepo = productCategoryRepo;
        this.productImageRepo = productImageRepo;
        this.sellerProfileRepo = sellerProfileRepo;
        this.authClient = authClient;
        this.addressRepo = addressRepo;
    }

    @Override
    public Product addProduct(Long sellerId, String name, Long categoryId, String description, BigDecimal price, List<String> imageUrls) throws UserNotFoundException, UnauthorizedUserException {
        // check if the given product category is valid
        ProductCategory productCategory = this.productCategoryRepo.findById(categoryId)
                .orElseThrow(() -> new ProductCategoryNotFoundException(categoryId));
        // create new product
        Product product = new Product();
        product.setName(name);
        product.setCategory(productCategory);
        product.setDescription(description);
        product.setPrice(price);
        product.setSellerId(sellerId);
        this.productRepo.save(product);
        // save product image if any
        if (!imageUrls.isEmpty()) {
            ProductImage productImage = new ProductImage();
            productImage.setProduct(product);
            productImage.setImageUrls(imageUrls);
            this.productImageRepo.save(productImage);
            product.setImage(productImage);
            this.productRepo.save(product);
        }
        return product;
    }

    @Override
    public ProductInventory updateProductInventory(Long sellerId, Long productId, Integer quantity) throws UserNotFoundException, UnauthorizedUserException, ProductNotFoundException {
        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        // ensure the product is listed by the given seller
        if (!product.getSellerId().equals(sellerId))
            throw new UnauthorizedUserException(sellerId);
        // create or update product inventory
        Optional<ProductInventory> productInventoryOptional = this.productInventoryRepo.findByProductId(productId);
        ProductInventory productInventory;
        if (productInventoryOptional.isPresent()) {  // update
            productInventory = productInventoryOptional.get();
            quantity += productInventory.getQuantity();  // adding to the current quantity
        } else {  // create
            productInventory = new ProductInventory();
            productInventory.setProduct(product);
        }
        productInventory.setQuantity(quantity);
        this.productInventoryRepo.save(productInventory);
        // respecting bidirectional relationship with Product
        product.setInventory(productInventory);
        this.productRepo.save(product);
        return productInventory;
    }

    @Override
    public SellerProfileResponseDto getProfile(Long sellerId) {
        SellerProfile sellerProfile = this.sellerProfileRepo.findBySellerId(sellerId)
                .orElse(null);
        List<Address> addresses = this.addressRepo.findByUserId(sellerId);
        UserInfoDto userInfo = this.authClient.getUserInfo(sellerId);
        return SellerProfileResponseDto.from(userInfo, sellerProfile, addresses);
    }
}
