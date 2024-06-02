package com.colvir.shop.repository;

import com.colvir.shop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class ProductRepository {

    private final Set<Product> products = new HashSet<>();

    public Product save(Product product) {
        products.add(product);
        return product;
    }

    public Product getByArticle(String article) {
        return products.stream()
                .filter(product -> product.getArticle().equals(article))
                .findFirst()
                .orElse(null);
    }

    public Set<Product> getProducts() {
        return products;
    }
}
