package com.colvir.shop.repository;

import com.colvir.shop.model.Category;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@Getter
public class CategoryRepository {

    private final Set<Category> categories = new HashSet<>();

    public Category save(Category category) {
        categories.add(category);
        return category;
    }

    public Category getByCode(String categoryCode) {
        return categories.stream()
                .filter(category -> category.getCode().equals(categoryCode))
                .findFirst()
                .orElse(null);
    }
}
