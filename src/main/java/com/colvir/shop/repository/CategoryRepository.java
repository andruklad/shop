package com.colvir.shop.repository;

import com.colvir.shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Реализация с использованием Spring Data JPA

    Category findByCode(String code);

    List<Category> findAllByCatalogId(Integer catalogId);
}
