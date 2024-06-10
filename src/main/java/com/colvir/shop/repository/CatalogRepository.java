package com.colvir.shop.repository;

import com.colvir.shop.model.Catalog;

import java.util.Set;

public interface CatalogRepository {

    Catalog save(Catalog catalog);

    Catalog getByCode(String catalogCode);

    Catalog update(Catalog catalogForUpdate);

    void deleteByCode(String categoryCode);

    Set<Catalog> getCatalogs();
}
