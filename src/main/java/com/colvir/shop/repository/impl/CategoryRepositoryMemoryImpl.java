package com.colvir.shop.repository.impl;

import com.colvir.shop.model.Category;
import com.colvir.shop.repository.CategoryRepository;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class CategoryRepositoryMemoryImpl implements CategoryRepository {

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

    public Category update(Category categoryForUpdate) {

        Category category = getByCode(categoryForUpdate.getCode());

        if (category != null) {
            category.setName(categoryForUpdate.getName());
            category.setCatalogCode(categoryForUpdate.getCatalogCode());
        }

        return category;
    }

    public void deleteByCode(String categoryCode) {

        categories.remove(getByCode(categoryCode));
    }
}
