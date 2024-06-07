package com.colvir.shop.mapper;

import com.colvir.shop.dto.CatalogWithCategories;
import com.colvir.shop.dto.CatalogsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CatalogsMapper {

    default CatalogsResponse catalogsToCatalogsResponse(Set<CatalogWithCategories> catalogsWithCategories) {

        return new CatalogsResponse(catalogsWithCategories);
    }
}
