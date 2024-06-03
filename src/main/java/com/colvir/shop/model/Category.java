package com.colvir.shop.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Category {

    private final String code;

    private final String name;

    private final String catalogCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(code, category.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
