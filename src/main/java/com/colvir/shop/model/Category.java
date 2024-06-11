package com.colvir.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Category {

    private Long id;

    private String code;

    private String name;

    private String catalogCode;

    public Category(String code, String name, String catalogCode) {
        this.code = code;
        this.name = name;
        this.catalogCode = catalogCode;
    }

    // Пустой конструктор для mapstruct
    public Category() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(code, category.code) && Objects.equals(name, category.name) && Objects.equals(catalogCode, category.catalogCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, catalogCode);
    }
}
