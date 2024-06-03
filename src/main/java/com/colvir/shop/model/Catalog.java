package com.colvir.shop.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Catalog {

    private final String code;

    private final String name;

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
