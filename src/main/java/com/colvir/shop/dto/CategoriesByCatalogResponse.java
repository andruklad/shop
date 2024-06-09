package com.colvir.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CategoriesByCatalogResponse {

    private Set<CategoryWithProducts> categories;
}
