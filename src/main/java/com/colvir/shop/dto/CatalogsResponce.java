package com.colvir.shop.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CatalogsResponce {

    private Set<CatalogWithCategories> catalogs;
}
