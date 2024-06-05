package com.colvir.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CatalogsResponce {

    private Set<CatalogWithCategories> catalogs;
}
