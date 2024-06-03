package com.colvir.shop.mapper;

import com.colvir.shop.dto.CatalogWithCategories;
import com.colvir.shop.dto.CatalogsResponce;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CatalogsMapper {

    public CatalogsResponce catalogsToCatalogsResponce(Set<CatalogWithCategories> catalogsWithCategories) {

        CatalogsResponce catalogsResponce = new CatalogsResponce();
        catalogsResponce.setCatalogs(catalogsWithCategories);
        return catalogsResponce;
    }
}
