package com.colvir.shop.repository.impl;

import com.colvir.shop.model.Product;
import com.colvir.shop.repository.ProductRepository;
import lombok.Getter;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Getter
public class ProductRepositoryPostgresImpl implements ProductRepository {
    // TODO. Переработать под использование БД

    private final Set<Product> products = new HashSet<>();

    private final Random random = new Random();

    public Product save(Product product) {

        if (product.getId() == null) {

            product.setId(random.nextInt());
        }
        products.add(product);
        return product;
    }

    public Product getByArticle(String article) {

        return products.stream()
                .filter(product -> product.getArticle().equals(article))
                .findFirst()
                .orElse(null);
    }

    public Product update(Product productForUpdate) {

        Product product = getByArticle(productForUpdate.getArticle());

        if (product != null) {
            product.setName(productForUpdate.getName());
            product.setPrice(productForUpdate.getPrice());
            product.setCategoryId(productForUpdate.getCategoryId());
        }

        return product;
    }

    public void deleteByArticle(String article) {

        products.remove(getByArticle(article));
    }
}
