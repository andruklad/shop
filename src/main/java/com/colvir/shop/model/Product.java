package com.colvir.shop.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Product {

    private final String article;

    private final String name;

    private final Double price;

    private final String categoryCode;

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
