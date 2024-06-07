package com.colvir.shop.mapper;

import com.colvir.shop.dto.CategoriesByCatalogResponse;
import com.colvir.shop.dto.CategoryWithProducts;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoriesByCatalogMapper {

    default CategoriesByCatalogResponse categoriesToCategoriesByCatalogResponse(Set<CategoryWithProducts> categoriesWithProducts) {

        return new CategoriesByCatalogResponse(categoriesWithProducts);
    }
}
