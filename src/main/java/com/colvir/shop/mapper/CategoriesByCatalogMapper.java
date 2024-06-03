package com.colvir.shop.mapper;

import com.colvir.shop.dto.CategoriesByCatalogResponce;
import com.colvir.shop.dto.CategoryWithProducts;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CategoriesByCatalogMapper {

    public CategoriesByCatalogResponce categoriesToCategoriesByCatalogResponce(Set<CategoryWithProducts> categoriesWithProducts) {
        CategoriesByCatalogResponce categoriesByCatalogResponce = new CategoriesByCatalogResponce();
        categoriesByCatalogResponce.setCategories(categoriesWithProducts);
        return categoriesByCatalogResponce;
    }
}
