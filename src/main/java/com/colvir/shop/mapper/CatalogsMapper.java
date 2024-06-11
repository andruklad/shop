package com.colvir.shop.mapper;

import com.colvir.shop.dto.CatalogRequest;
import com.colvir.shop.dto.CatalogWithCategories;
import com.colvir.shop.dto.CatalogsResponse;
import com.colvir.shop.model.Catalog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CatalogsMapper {

    @Mapping(target = "id", ignore = true)
    Catalog catalogRequestToCatalog(CatalogRequest catalogRequest);

    default CatalogsResponse catalogsToCatalogsResponse(Set<CatalogWithCategories> catalogsWithCategories) {

        return new CatalogsResponse(catalogsWithCategories);
    }
}
