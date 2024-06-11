package com.colvir.shop.repository;

import com.colvir.shop.model.Category;

import java.util.Set;

public interface CategoryRepository {

    Category save(Category category);

    Category getByCode(String categoryCode);

    Category update(Category categoryForUpdate);

    void deleteByCode(String categoryCode);

    Set<Category> getCategories();
}
