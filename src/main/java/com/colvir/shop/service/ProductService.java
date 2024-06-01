package com.colvir.shop.service;

import com.colvir.shop.model.Product;
import com.colvir.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }
}
