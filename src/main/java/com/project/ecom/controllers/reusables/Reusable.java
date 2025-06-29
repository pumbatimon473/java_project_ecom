package com.project.ecom.controllers.reusables;

import com.project.ecom.dtos.*;
import com.project.ecom.models.*;
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
        productDto.setSellerId(product.getSellerId());
//        productDto.setSoldBy(product.getSeller().getName());
        return productDto;
    }

    public static SellerDto mapSellerToSellerDto(Seller seller) {
        SellerDto sellerDto = new SellerDto();
        sellerDto.setSellerId(seller.getId());
        sellerDto.setName(seller.getName());
        sellerDto.setEmail(seller.getEmail());
//        sellerDto.setPhoneNumber(seller.getPhoneNumber());
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
        productDto.setSellerId(product.getSellerId());
//        SellerDto sellerDto = Reusable.mapSellerToSellerDto(product.getSeller());
//        productDto.setSoldBy(sellerDto);

        return productDto;
    }

    public static ProductInCartDto mapProductToProductInCartDto(Product product) {
        ProductInCartDto productDto = new ProductInCartDto();
        productDto.setProductId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    public static PaymentDto mapPaymentToPaymentDto(Payment payment) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setOrderId(payment.getOrder().getId());
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setStatus(payment.getStatus());
        paymentDto.setTransactionId(payment.getTransactionId());
        return paymentDto;
    }

    public static GetOrdersResponseDto mapPageToGetOrdersResponseDto(Page<Order> page) {
        GetOrdersResponseDto responseDto = new GetOrdersResponseDto();
        responseDto.setCurrentPage(page.getNumber());
        responseDto.setPageSize(page.getSize());
        responseDto.setTotalCount(page.getTotalElements());
        responseDto.setTotalPages(page.getTotalPages());
        responseDto.setOrders(page.getContent().stream().map(OrderDto::from).toList());  // Note: the list is immutable
        return responseDto;
    }
}
