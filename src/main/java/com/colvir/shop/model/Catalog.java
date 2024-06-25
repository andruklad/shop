package com.colvir.shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "catalogs")
@Entity
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catalog_seq")
    @SequenceGenerator(name = "catalog_seq", sequenceName = "sequence_catalog_id", allocationSize = 1)
    private Integer id;

    private String code;

    private String name;

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
