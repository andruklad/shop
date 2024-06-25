package com.colvir.shop.repository;

import com.colvir.shop.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
    // Реализация с использованием Spring Data JPA

    Catalog findByCode(String code);
}
