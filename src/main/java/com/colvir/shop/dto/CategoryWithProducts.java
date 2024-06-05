package com.colvir.shop.dto;

import com.colvir.shop.model.Category;
import com.colvir.shop.model.Product;
import lombok.Data;

import java.util.Set;

@Data
public class CategoryWithProducts {

    private Category category;

    private Set<Product> products;

    public CategoryWithProducts(Category category, Set<Product> products) {

        this.category = category;
        this.products = products;
    }
}
