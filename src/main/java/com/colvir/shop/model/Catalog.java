package com.colvir.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Catalog {

    private Integer id;

    private String code;

    private String name;

    public Catalog(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Пустой конструктор для mapstruct
    public Catalog() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(id, catalog.id) && Objects.equals(code, catalog.code) && Objects.equals(name, catalog.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name);
    }
}
