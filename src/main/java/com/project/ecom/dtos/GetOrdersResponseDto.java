package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetOrdersResponseDto {
    private List<OrderDto> orders;
    private Integer currentPage;
    private Integer pageSize;
    private Long totalCount;
    private Integer totalPages;
}

