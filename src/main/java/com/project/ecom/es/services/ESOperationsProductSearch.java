package com.project.ecom.es.services;

import com.project.ecom.es.mapper.ESProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Primary
@Service
public class ESOperationsProductSearch implements IProductSearchService {
    private final ElasticsearchOperations esOperations;

    @Autowired
    public ESOperationsProductSearch(ElasticsearchOperations esOperations) {
        this.esOperations = esOperations;
    }

    @Override
    public Page<ESProduct> searchProductsByName(String name, Pageable pageable) {
        Criteria searchCriteria = new Criteria("name").matches(name);
        CriteriaQuery criteriaQuery = new CriteriaQuery(searchCriteria, pageable);

        return executeSearchQuery(criteriaQuery, pageable);
    }

    @Override
    public Page<ESProduct> searchProducts(String query, Pageable pageable) {
        Criteria searchCriteria = new Criteria("name").matches(query)
                .or(new Criteria("category").matches(query))
                .or(new Criteria("description").matches(query));
        CriteriaQuery criteriaQuery = new CriteriaQuery(searchCriteria, pageable);

        return executeSearchQuery(criteriaQuery, pageable);
    }

    @Override
    public Page<ESProduct> searchProductsByQueryAndPriceRange(String query, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Criteria searchCriteria = new Criteria("name").matches(query)
                .or(new Criteria("category").matches(query))
                .or(new Criteria("description").matches(query));

        Criteria priceCriteria = new Criteria("price")
                .greaterThanEqual(minPrice)
                .lessThanEqual(maxPrice);

        Criteria combinedCriteria = searchCriteria.and(priceCriteria);
        CriteriaQuery criteriaQuery = new CriteriaQuery(combinedCriteria, pageable);

        return executeSearchQuery(criteriaQuery, pageable);
    }

    private PageImpl<ESProduct> executeSearchQuery(CriteriaQuery criteriaQuery, Pageable pageable) {
        SearchHits<ESProduct> hits = this.esOperations.search(criteriaQuery, ESProduct.class);
        return new PageImpl<>(hits.stream().map(SearchHit::getContent).toList(), pageable, hits.getTotalHits());
    }
}
