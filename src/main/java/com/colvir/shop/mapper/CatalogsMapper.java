package com.colvir.shop.mapper;

import com.colvir.shop.dto.CatalogWithCategories;
import com.colvir.shop.dto.CatalogsResponce;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CatalogsMapper {

    default CatalogsResponce catalogsToCatalogsResponce(Set<CatalogWithCategories> catalogsWithCategories) {

        return new CatalogsResponce(catalogsWithCategories);
    }
}
