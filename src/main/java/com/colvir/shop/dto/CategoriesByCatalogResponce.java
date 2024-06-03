package com.colvir.shop.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CategoriesByCatalogResponce {

    private Set<CategoryWithProducts> categories;
}
