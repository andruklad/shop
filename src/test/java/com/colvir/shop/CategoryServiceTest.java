package com.colvir.shop;

import com.colvir.shop.dto.CategoriesByCatalogResponse;
import com.colvir.shop.dto.CategoryRequest;
import com.colvir.shop.dto.CategoryWithProducts;
import com.colvir.shop.expception.CategoryNotFoundException;
import com.colvir.shop.mapper.CategoriesMapper;
import com.colvir.shop.mapper.CategoriesMapperImpl;
import com.colvir.shop.mapper.ProductsByCategoryMapperImpl;
import com.colvir.shop.model.Catalog;
import com.colvir.shop.model.Category;
import com.colvir.shop.model.Product;
import com.colvir.shop.repository.CatalogRepository;
import com.colvir.shop.repository.CategoryRepository;
import com.colvir.shop.repository.ProductRepository;
import com.colvir.shop.service.CategoryService;
import com.colvir.shop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        CategoryService.class,
        ProductService.class,
        CategoriesMapperImpl.class,
        ProductsByCategoryMapperImpl.class
})
public class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoriesMapper categoriesMapper;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    CatalogRepository catalogRepository;

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @Test
    void save_success() {
        //Подготовка входных данных
        CategoryRequest categoryRequest = new CategoryRequest("CategoryCode1", "CategoryName1", "CatalogCode1");
        Category category = categoriesMapper.categoryRequestToCategory(categoryRequest);
        category.setCatalogId(1);

        //Подготовка ожидаемого результата
        Category expectedCategory = category;
        Catalog catalog = new Catalog(1, "CatalogCode1", "CatalogName1");

        when(categoryRepository.save(category)).thenReturn(category);
        when(catalogRepository.getByCode("CatalogCode1")).thenReturn(catalog);

        //Начало теста
        Category actualCategory = categoryService.save(categoryRequest);
        assertEquals(expectedCategory, actualCategory);
        verify(categoryRepository).save(any());
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void getByCode_success() {
        // Подготовка входных данных
        Category category = new Category(1, "CategoryCode1", "CategoryName1", 1);

        // Подготовка ожидаемого результата
        Category expectedCategory = category;

        when(categoryRepository.getByCode(category.getCode())).thenReturn(category);

        // Начало теста
        Category actualCategory = categoryService.getByCode(category.getCode());
        assertEquals(expectedCategory, actualCategory);
        verify(categoryRepository).getByCode(category.getCode());
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void getByCode_withException() {
        // Подготовка входных данных
        String categoryCode = "CategoryCode1";

        // Подготовка ожидаемого результата
        String expectedMessage = String.format("Категория с кодом %s не найдена", categoryCode);

        when(categoryRepository.getByCode(categoryCode)).thenReturn(null);

        // Начало теста
        Exception exception = assertThrows(CategoryNotFoundException.class, () -> categoryService.getByCode(categoryCode));
        assertEquals(expectedMessage, exception.getMessage());
        verify(categoryRepository).getByCode(categoryCode);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void update_success() {
        //Подготовка входных данных
        CategoryRequest categoryRequest = new CategoryRequest("CategoryCode1", "CategoryName2", "CatalogCode2");
        Category categoryFromRequest = new Category(1, "CategoryCode1", "CategoryName2", 2);
        Category categoryFromRepository = new Category(1, "CategoryCode1", "CategoryName1", 1);

        //Подготовка ожидаемого результата
        Category expectedCategory = categoryFromRequest;
        Catalog expectedCatalog = new Catalog(2, "CatalogCode2", "CatalogName2");

        when(categoryRepository.getByCode(categoryRequest.getCode())).thenReturn(categoryFromRepository);
        when(categoryRepository.update(categoryFromRequest)).thenReturn(expectedCategory);
        when(catalogRepository.getByCode("CatalogCode2")).thenReturn(expectedCatalog);

        //Начало теста
        Category actualProduct = categoryService.update(categoryRequest);
        assertEquals(expectedCategory, actualProduct);
        verify(categoryRepository).getByCode(any());
        verify(categoryRepository).update(any());
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void deleteByCode_success() {
        // Подготовка входных данных
        Category category = new Category(1, "CategoryCode1", "CategoryName1", 1);

        // Подготовка ожидаемого результата
        String code = category.getCode();

        when(categoryRepository.getByCode(code)).thenReturn(category);

        // Начало теста
        categoryService.deleteByCode(category.getCode());
        verify(categoryRepository).getByCode(code);
        verify(categoryRepository).deleteByCode(category.getCode());
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void getAllCategoriesByCatalog_success() {
        // Подготовка входных данных
        String catalogCode = "CatalogCode1";
        Catalog catalog = new Catalog(1, catalogCode, "CatalogName1");

        // Подготовка ожидаемого результата
        Category category1 = new Category(1, "CategoryCode1", "CategoryName1", 1);
        Category category2 = new Category(2, "CategoryCode2", "CategoryName2", 1);
        Category category3 = new Category(3, "CategoryCode3", "CategoryName3", 2);

        Product product1 = new Product("001", "ProductName1", 10.0, "CategoryCode1");
        Product product2 = new Product("002", "ProductName2", 20.0, "CategoryCode1");
        Product product3 = new Product("003", "ProductName3", 30.0, "CategoryCode2");

        CategoryWithProducts categoryWithProducts1 = new CategoryWithProducts(category1, new HashSet<>(Arrays.asList(product1, product2)));
        CategoryWithProducts categoryWithProducts2 = new CategoryWithProducts(category2, new HashSet<>(List.of(product3)));
        CategoriesByCatalogResponse expectedCategoriesByCatalogResponse =
                new CategoriesByCatalogResponse(new HashSet<>(Arrays.asList(categoryWithProducts1, categoryWithProducts2)));

        when(categoryRepository.getCategories()).thenReturn(new HashSet<>(Arrays.asList(category1, category2, category3)));
        when(productRepository.getProducts()).thenReturn(new HashSet<>(Arrays.asList(product1, product2, product3)));
        when(catalogRepository.getByCode("CatalogCode1")).thenReturn(catalog);

        // Начало теста
        CategoriesByCatalogResponse actualCategoriesByCatalogResponse = categoryService.getAllCategoriesByCatalog(catalogCode);
        assertEquals(expectedCategoriesByCatalogResponse, actualCategoriesByCatalogResponse);
        verify(categoryRepository).getCategories();
        verifyNoMoreInteractions(categoryRepository);
    }
}
