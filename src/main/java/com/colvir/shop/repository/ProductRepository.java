package com.colvir.shop.repository;

import com.colvir.shop.model.Product;

import java.util.Set;

public interface ProductRepository {

    Product save(Product product);

    Product getByArticle(String article);

    Product update(Product productForUpdate);

    void deleteByArticle(String article);

    Set<Product> getProducts();
}
