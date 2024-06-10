package com.colvir.shop.repository.impl;

import com.colvir.shop.model.Catalog;
import com.colvir.shop.repository.CatalogRepository;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class CatalogRepositoryPostgresImpl implements CatalogRepository {
    // TODO. Пока это копия CatalogRepositoryMemoryImpl. Необходимо адаптировать на работу с БД

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

    public Catalog update(Catalog catalogForUpdate) {

        Catalog catalog = getByCode(catalogForUpdate.getCode());

        if (catalog != null) {
            catalog.setName(catalogForUpdate.getName());
        }

        return catalog;
    }

    public void deleteByCode(String categoryCode) {

        catalogs.remove(getByCode(categoryCode));
    }
}
