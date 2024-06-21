package com.colvir.shop.service;

import com.colvir.shop.dto.CategoriesByCatalogResponse;
import com.colvir.shop.dto.CategoryRequest;
import com.colvir.shop.dto.CategoryWithProducts;
import com.colvir.shop.expception.CatalogNotFoundException;
import com.colvir.shop.expception.CategoryNotFoundException;
import com.colvir.shop.mapper.CategoriesMapper;
import com.colvir.shop.model.Catalog;
import com.colvir.shop.model.Category;
import com.colvir.shop.repository.CatalogRepository;
import com.colvir.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CatalogRepository catalogRepository;

    private final ProductService productService;

    private final CategoriesMapper categoriesMapper;

    public Category save(CategoryRequest categoryRequest) {

        Category category = categoriesMapper.categoryRequestToCategory(categoryRequest);
        fillCatalogIdByCatalogCode(category, categoryRequest.getCatalogCode());

        return categoryRepository.save(category);
    }

    public Category getByCode(String categoryCode) {

        Category category = categoryRepository.findByCode(categoryCode);

        if (category == null) {
            throw new CategoryNotFoundException(String.format("Категория с кодом %s не найдена", categoryCode));
        }

        return category;
    }

    private void fillCategoryIdByCode(Category category) {

        Category categoryByCode = getByCode(category.getCode());
        category.setId(categoryByCode.getId());
    }

    private Integer getCatalogIdByCatalogCode(String catalogCode) {

        Catalog catalog = catalogRepository.findByCode(catalogCode);

        if (catalog == null) {
            throw new CatalogNotFoundException(String.format("Каталог с кодом %s не найден", catalogCode));
        }

        return catalog.getId();
    }

    private void fillCatalogIdByCatalogCode(Category category, String catalogCode) {

        Integer catalogId = getCatalogIdByCatalogCode(catalogCode);
        category.setCatalogId(catalogId);
    }

    public Category update(CategoryRequest categoryRequest) {

        Category category = categoriesMapper.categoryRequestToCategory(categoryRequest);
        fillCategoryIdByCode(category);
        fillCatalogIdByCatalogCode(category, categoryRequest.getCatalogCode());

        return categoryRepository.save(category);
    }

    public void deleteByCode(String categoryCode) {

        // Проверка наличия, чтобы сообщить об ошибке в случае отсутствия
        Category category = getByCode(categoryCode);

        categoryRepository.deleteById(category.getId());
    }

    public CategoriesByCatalogResponse getAllCategoriesByCatalog(String catalogCode) {

        Integer catalogId = getCatalogIdByCatalogCode(catalogCode);
        // Отбор категорий из указанного каталога
        Set<Category> categories = new HashSet<>(categoryRepository.findAllByCatalogId(catalogId));

        // Заполнение продуктов каждой категории для объекта-ответа
        Set<CategoryWithProducts> categoriesWithProducts = categories.stream()
                .map(category -> new CategoryWithProducts(category, productService.getAllProductsByCategory(category.getCode()).getProducts()))
                .collect(Collectors.toSet());

        // Маппинг в объект-ответ
        return categoriesMapper.categoriesToCategoriesByCatalogResponse(categoriesWithProducts);
    }

    private void addCategory(String categoryCode, String categoryName, String catalogCode) {

        CategoryRequest categoryRequest = new CategoryRequest(categoryCode, categoryName, catalogCode);
        save(categoryRequest);
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
