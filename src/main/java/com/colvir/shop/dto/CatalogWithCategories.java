package com.colvir.shop.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CatalogWithCategories {

    private String catalogCode;

    private Set<CategoryWithProducts> categoriesWithProducts;

    public CatalogWithCategories(String catalogCode, Set<CategoryWithProducts> categoriesWithProducts) {
        this.catalogCode = catalogCode;
        this.categoriesWithProducts = categoriesWithProducts;
    }
}
