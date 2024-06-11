package com.colvir.shop.mapper;

import com.colvir.shop.dto.CategoriesByCatalogResponse;
import com.colvir.shop.dto.CategoryRequest;
import com.colvir.shop.dto.CategoryWithProducts;
import com.colvir.shop.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoriesMapper {

    @Mapping(target = "id", ignore = true)
    Category categoryRequestToCategory(CategoryRequest categoryRequest);

    default CategoriesByCatalogResponse categoriesToCategoriesByCatalogResponse(Set<CategoryWithProducts> categoriesWithProducts) {

        return new CategoriesByCatalogResponse(categoriesWithProducts);
    }
}
