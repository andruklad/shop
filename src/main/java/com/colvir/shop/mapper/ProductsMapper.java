package com.colvir.shop.mapper;

import com.colvir.shop.dto.ProductRequest;
import com.colvir.shop.dto.ProductsByCategoryResponse;
import com.colvir.shop.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoryId", ignore = true)
    Product productRequestToProduct(ProductRequest productRequest);

    default ProductsByCategoryResponse productsToProductsByCategoryResponse(Set<Product> products) {

        return new ProductsByCategoryResponse(products);
    }
}
