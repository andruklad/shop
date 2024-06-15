package com.colvir.shop.repository.impl;

import com.colvir.shop.model.Catalog;
import com.colvir.shop.repository.CatalogRepository;
import lombok.Getter;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Getter
public class CatalogRepositoryMemoryImpl implements CatalogRepository {

    private final Set<Catalog> catalogs = new HashSet<>();

    private final Random random = new Random();

    public Catalog save(Catalog catalog) {

        if (catalog.getId() == null) {

            catalog.setId(random.nextInt());
        }
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
