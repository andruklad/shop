package com.colvir.shop.dto;

import com.colvir.shop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProductsByCategoryResponce {

    private Set<Product> products;
}
