package com.project.ecom.es.dto;

import com.project.ecom.es.mapper.ESProduct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class SearchProductsResponseDto {
    private List<ESProduct> products;
    private Integer currentPage;
    private Integer pageSize;
    private Long totalCount;
    private Integer totalPages;

    public static SearchProductsResponseDto from(Page<ESProduct> page) {
        SearchProductsResponseDto responseDto = new SearchProductsResponseDto();
        responseDto.setCurrentPage(page.getNumber());
        responseDto.setPageSize(page.getSize());
        responseDto.setTotalCount(page.getTotalElements());
        responseDto.setTotalPages(page.getTotalPages());
        responseDto.setProducts(page.getContent());
        return responseDto;
    }
}
