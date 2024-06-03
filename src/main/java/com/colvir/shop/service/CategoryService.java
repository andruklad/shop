package com.colvir.shop.service;

import com.colvir.shop.dto.CategoriesByCatalogResponce;
import com.colvir.shop.dto.CategoryWithProducts;
import com.colvir.shop.mapper.CategoriesByCatalogMapper;
import com.colvir.shop.model.Category;
import com.colvir.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ProductService productService;

    private final CategoriesByCatalogMapper categoriesByCatalogMapper;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public CategoriesByCatalogResponce getAllCategoriesByCatalog(String catalogCode) {
        // Отбор категорий из указанного каталога
        Set<Category> categories = categoryRepository.getCategories().stream()
                .filter(category -> category.getCatalogCode().equals(catalogCode))
                .collect(Collectors.toSet());

        // Заполнение продуктов каждой категории для объекта-ответа
        Set<CategoryWithProducts> categoriesWithProducts = categories.stream()
                .map(category -> new CategoryWithProducts(category.getCode(), productService.getAllProductsByCategory(category.getCode()).getProducts()))
                .collect(Collectors.toSet());

        // Маппинг в объект-ответ
        return categoriesByCatalogMapper.categoriesToCategoriesByCatalogResponce(categoriesWithProducts);
    }

    private void addCategory(String categoryCode, String categoryName, String catalogCode) {
        Category category = new Category(categoryCode, categoryName, catalogCode);
        save(category);
    }

    public void loadTestData() {
        // CatalogCode1
        addCategory("CategoryCode1", "CategoryName1", "CatalogCode1");
        addCategory("CategoryCode2", "CategoryName2", "CatalogCode1");
        // CatalogCode2
        addCategory("CategoryCode3", "CategoryName3", "CatalogCode2");
        addCategory("CategoryCode4", "CategoryName4", "CatalogCode2");
        // CatalogCode3
        addCategory("CategoryCode5", "CategoryName5", "CatalogCode3");
        addCategory("CategoryCode6", "CategoryName6", "CatalogCode3");
    }
}
