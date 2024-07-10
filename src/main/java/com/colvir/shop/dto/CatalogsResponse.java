package com.colvir.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class CatalogsResponse {

    private Set<CatalogWithCategories> catalogs;
}
