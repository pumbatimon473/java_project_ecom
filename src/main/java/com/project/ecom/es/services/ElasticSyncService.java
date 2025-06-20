package com.project.ecom.es.services;

import com.project.ecom.es.model_mapper.ProductMapper;
import com.project.ecom.es.mapper.ESProduct;
import com.project.ecom.es.repository.ESProductRepository;
import com.project.ecom.models.Product;
import com.project.ecom.repositories.IProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticSyncService {
    private final ESProductRepository esProductRepo;
    private final IProductRepository productRepo;

    @Autowired
    public ElasticSyncService(ESProductRepository esProductRepo, IProductRepository productRepo) {
        this.esProductRepo = esProductRepo;
        this.productRepo = productRepo;
    }

    /* Issue with @PostConstruct:
    @PostConstruct  // executes on application startup after all the beans are created and dependencies are injected

    Issue:
    org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.project.ecom.models.ProductImage.imageUrls: could not initialize proxy - no Session

    Cause: method is executing outside a @Transactional context
     */

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void SyncProducts() {
        List<Product> products = this.productRepo.findAll();
        List<ESProduct> esProducts = products.stream().map(ProductMapper::mapToESProduct).toList();
        this.esProductRepo.saveAll(esProducts);
    }
}
