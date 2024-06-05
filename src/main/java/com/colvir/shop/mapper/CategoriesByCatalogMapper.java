package com.colvir.shop.mapper;

import com.colvir.shop.dto.CategoriesByCatalogResponce;
import com.colvir.shop.dto.CategoryWithProducts;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoriesByCatalogMapper {

    default CategoriesByCatalogResponce categoriesToCategoriesByCatalogResponce(Set<CategoryWithProducts> categoriesWithProducts) {

        return new CategoriesByCatalogResponce(categoriesWithProducts);
    }
}
