package com.colvir.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Catalog {

    private String code;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(code, catalog.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
