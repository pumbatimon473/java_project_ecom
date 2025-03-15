package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetProductsResponseDto {
    private List<ProductDto> products;
    private Integer currentPage;
    private Integer pageSize;
    private Long totalCount;
    private Integer totalPages;
}
