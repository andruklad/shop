package com.colvir.shop.generator;

import com.colvir.shop.dto.CatalogRequest;
import com.colvir.shop.dto.CatalogWithCategories;
import com.colvir.shop.dto.CatalogsResponse;
import com.colvir.shop.dto.CategoryWithProducts;
import com.colvir.shop.model.Catalog;
import com.colvir.shop.model.Category;
import com.colvir.shop.model.Product;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CatalogDtoGenerator {

    public static CatalogRequest.CatalogRequestBuilder createCatalogRequestBuilder() {

        return CatalogRequest.builder()
                .setCode(CatalogGenerator.CATALOG_CODE)
                .setName(CatalogGenerator.CATALOG_NAME);
    }

    public static CatalogsResponse.CatalogsResponseBuilder createCatalogsResponseBuilder() {

        Catalog catalog1 = CatalogGenerator.createCatalogBuilder().build();
        Catalog catalog2 = CatalogGenerator.createCatalog2Builder().build();

        Category category1 = new Category(1, "CategoryCode1", "CategoryName1", catalog1.getId());
        Category category2 = new Category(2, "CategoryCode2", "CategoryName2", catalog2.getId());

        Product product1 = new Product(1, "001", "ProductName1", 10.0, 1);
        Product product2 = new Product(2,"002", "ProductName2", 20.0, 2);

        CategoryWithProducts categoryWithProducts1 = new CategoryWithProducts(category1, new HashSet<>(List.of(product1)));
        CategoryWithProducts categoryWithProducts2 = new CategoryWithProducts(category2, new HashSet<>(List.of(product2)));

        CatalogWithCategories catalogWithCategories1 = new CatalogWithCategories(catalog1.getCode(), new HashSet<>(List.of(categoryWithProducts1)));
        CatalogWithCategories catalogWithCategories2 = new CatalogWithCategories(catalog2.getCode(), new HashSet<>(List.of(categoryWithProducts2)));

        return CatalogsResponse.builder()
                .setCatalogs(new HashSet<>(Arrays.asList(catalogWithCategories1, catalogWithCategories2)));
    }
}
