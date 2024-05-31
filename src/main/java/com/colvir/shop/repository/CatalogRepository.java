package com.colvir.shop.repository;

import com.colvir.shop.model.Catalog;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class CatalogRepository {

    private final Set<Catalog> catalogs = new HashSet<>();

    public Catalog save(Catalog catalog) {
        catalogs.add(catalog);
        return catalog;
    }

    public Catalog getByCode(String catalogCode) {
        return catalogs.stream()
                .filter(catalog -> catalog.getCode().equals(catalogCode))
                .findFirst()
                .orElse(null);
    }
}
