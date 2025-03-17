package com.project.ecom.controllers.reusables;

import com.project.ecom.dtos.*;
import com.project.ecom.models.OrderItem;
import com.project.ecom.models.Product;
import com.project.ecom.models.Seller;
import org.springframework.data.domain.Page;

/*
Contains common mapping methods which maps model objects to their corresponding dtos.
 */
public class Reusable {
    public static GetProductsResponseDto mapPageToGetProductsResponseDto(Page<Product> page) {
        GetProductsResponseDto responseDto = new GetProductsResponseDto();
        responseDto.setCurrentPage(page.getNumber());
        responseDto.setPageSize(page.getSize());
        responseDto.setTotalCount(page.getTotalElements());
        responseDto.setTotalPages(page.getTotalPages());
        responseDto.setProducts(page.getContent().stream().map(Reusable::mapProductToProductDto).toList());  // Note: the list is immutable
        return responseDto;
    }

    public static ProductDto mapProductToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getId());
        productDto.setName(product.getName());
        productDto.setCategory(product.getCategory().getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setPrimaryImage(product.getImage().getImageUrls().stream().findFirst().orElse(null));
        productDto.setSoldBy(product.getSeller().getName());
        return productDto;
    }

    public static SellerDto mapSellerToSellerDto(Seller seller) {
        SellerDto sellerDto = new SellerDto();
        sellerDto.setSellerId(seller.getId());
        sellerDto.setName(seller.getName());
        sellerDto.setEmail(seller.getEmail());
        sellerDto.setPhoneNumber(seller.getPhoneNumber());
        return sellerDto;
    }

    public static ProductDetailsDto mapProductToProductDetailsDto(Product product) {
        ProductDetailsDto productDto = new ProductDetailsDto();
        productDto.setProductId(product.getId());
        productDto.setName(product.getName());
        productDto.setCategory(product.getCategory().getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImage(product.getImage());

        SellerDto sellerDto = Reusable.mapSellerToSellerDto(product.getSeller());
        productDto.setSoldBy(sellerDto);
        return productDto;
    }

    public static ProductInCartDto mapProductToProductInCartDto(Product product) {
        ProductInCartDto productDto = new ProductInCartDto();
        productDto.setProductId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    public static OrderItemDto mapOrderItemToOrderItemDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProduct(Reusable.mapProductToProductInCartDto(orderItem.getProduct()));
        orderItemDto.setQuantity(orderItem.getQuantity());
        return orderItemDto;
    }
}
