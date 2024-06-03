package com.colvir.shop.dto;

import com.colvir.shop.model.Product;
import lombok.Data;

import java.util.Set;

@Data
public class CategoryWithProducts {

    private String categoryCode;

    private Set<Product> products;

    public CategoryWithProducts(String categoryCode, Set<Product> products) {

        this.categoryCode = categoryCode;
        this.products = products;
    }
}
