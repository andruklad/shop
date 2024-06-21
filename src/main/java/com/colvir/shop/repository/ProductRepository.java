package com.colvir.shop.repository;

import com.colvir.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Реализация с использованием Spring Data JPA

    Product findByArticle(String article);

    Product findFirstByOrderByPriceDesc();

    List<Product> findAllByCategoryId(Integer categoryId);
}
