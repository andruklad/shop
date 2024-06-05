package com.colvir.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Product {

    private String article;

    private String name;

    private Double price;

    private String categoryCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(article, product.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article);
    }
}
