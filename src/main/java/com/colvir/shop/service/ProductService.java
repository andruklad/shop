package com.colvir.shop.service;

import com.colvir.shop.dto.ProductsByCategoryResponce;
import com.colvir.shop.mapper.ProductsByCategoryMapper;
import com.colvir.shop.model.Product;
import com.colvir.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductsByCategoryMapper productsByCategoryMapper;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public ProductsByCategoryResponce getAllProductsByCategory(String categoryCode) {
        Set<Product> products = productRepository.getProducts().stream()
                .filter(product -> product.getCategoryCode().equals(categoryCode))
                .collect(Collectors.toSet());

        return productsByCategoryMapper.productsToProductsByCategoryResponce(products);
    }
}
