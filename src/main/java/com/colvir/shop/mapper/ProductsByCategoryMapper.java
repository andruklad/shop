package com.colvir.shop.mapper;

import com.colvir.shop.dto.ProductsByCategoryResponse;
import com.colvir.shop.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductsByCategoryMapper {

    default ProductsByCategoryResponse productsToProductsByCategoryResponse(Set<Product> products) {

        return new ProductsByCategoryResponse(products);
    }
}
