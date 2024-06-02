package com.colvir.shop.mapper;

import com.colvir.shop.dto.ProductsByCategoryResponce;
import com.colvir.shop.model.Product;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProductsByCategoryMapper {

    public ProductsByCategoryResponce productsToProductsByCategoryResponce(Set<Product> products) {
        ProductsByCategoryResponce productsByCategoryResponce = new ProductsByCategoryResponce();
        productsByCategoryResponce.setProducts(products);
        return productsByCategoryResponce;
    }
}
