package com.colvir.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CatalogsResponse {

    private Set<CatalogWithCategories> catalogs;
}
