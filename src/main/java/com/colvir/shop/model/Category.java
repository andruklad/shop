package com.colvir.shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
@Table(name = "categories")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq", sequenceName = "sequence_category_id", allocationSize = 1)
    private Integer id;

    private String code;

    private String name;

    @Column(name = "catalog_id")
    private Integer catalogId;

    // Пустой конструктор для mapstruct
    public Category() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(code, category.code) && Objects.equals(name, category.name) && Objects.equals(catalogId, category.catalogId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, catalogId);
    }
}
