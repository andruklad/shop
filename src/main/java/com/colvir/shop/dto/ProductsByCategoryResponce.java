package com.colvir.shop.dto;

import com.colvir.shop.model.Product;
import lombok.Data;

import java.util.Set;

@Data
public class ProductsByCategoryResponce {

    private Set<Product> products;
}
