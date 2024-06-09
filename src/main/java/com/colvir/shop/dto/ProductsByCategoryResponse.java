package com.colvir.shop.dto;

import com.colvir.shop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProductsByCategoryResponse {

    private Set<Product> products;
}
