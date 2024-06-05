package com.colvir.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Category {

    private String code;

    private String name;

    private String catalogCode;

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
