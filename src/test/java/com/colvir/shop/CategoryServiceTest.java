package com.colvir.shop;

import com.colvir.shop.dto.CategoriesByCatalogResponce;
import com.colvir.shop.dto.CategoryWithProducts;
import com.colvir.shop.expception.CategoryNotFoundException;
import com.colvir.shop.mapper.CategoriesByCatalogMapperImpl;
import com.colvir.shop.mapper.ProductsByCategoryMapperImpl;
import com.colvir.shop.model.Category;
import com.colvir.shop.model.Product;
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
        CategoriesByCatalogMapperImpl.class,
        ProductsByCategoryMapperImpl.class
})
public class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @Test
    void save_success() {
        //Подготовка входных данных
        Category category = new Category("CategoryCode1", "CategoryName1", "CatalogCode1");

        //Подготовка ожидаемого результата
        Category expectedCategory = category;

        when(categoryRepository.save(category)).thenReturn(category);

        //Начало теста
        Category actualCategory = categoryService.save(category);
        assertEquals(expectedCategory, actualCategory);
        verify(categoryRepository).save(any());
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void getByCode_success() {
        // Подготовка входных данных
        Category category = new Category("CategoryCode1", "CategoryName1", "CatalogCode1");

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
        Category category = new Category("CategoryCode1", "CategoryName1", "CatalogCode1");

        //Подготовка ожидаемого результата
        Category expectedCategory = new Category("CategoryCode1", "CategoryName2", "CatalogCode2");

        when(categoryRepository.getByCode(category.getCode())).thenReturn(expectedCategory);
        when(categoryRepository.update(category)).thenReturn(expectedCategory);

        //Начало теста
        Category actualProduct = categoryService.update(category);
        assertEquals(expectedCategory, actualProduct);
        verify(categoryRepository).getByCode(any());
        verify(categoryRepository).update(any());
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void deleteByCode_success() {
        // Подготовка входных данных
        Category category = new Category("CategoryCode1", "CategoryName1", "CatalogCode1");

        // Подготовка ожидаемого результата
        String code = category.getCode();

        when(categoryRepository.getByCode(code)).thenReturn(category);

        // Начало теста
        categoryService.save(category);
        categoryService.deleteByCode(category.getCode());
        verify(categoryRepository).save(category);
        verify(categoryRepository).getByCode(code);
        verify(categoryRepository).deleteByCode(category.getCode());
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void getAllCategoriesByCatalog_success() {
        // Подготовка входных данных
        String catalogCode = "CatalogCode1";

        // Подготовка ожидаемого результата
        Category category1 = new Category("CategoryCode1", "CategoryName1", "CatalogCode1");
        Category category2 = new Category("CategoryCode2", "CategoryName2", "CatalogCode1");
        Category category3 = new Category("CategoryCode3", "CategoryName3", "CatalogCode2");

        Product product1 = new Product("001", "ProductName1", 10.0, "CategoryCode1");
        Product product2 = new Product("002", "ProductName2", 20.0, "CategoryCode1");
        Product product3 = new Product("003", "ProductName3", 30.0, "CategoryCode2");

        CategoryWithProducts categoryWithProducts1 = new CategoryWithProducts(category1, new HashSet<>(Arrays.asList(product1, product2)));
        CategoryWithProducts categoryWithProducts2 = new CategoryWithProducts(category2, new HashSet<>(List.of(product3)));
        CategoriesByCatalogResponce expectedCategoriesByCatalogResponce =
                new CategoriesByCatalogResponce(new HashSet<>(Arrays.asList(categoryWithProducts1, categoryWithProducts2)));

        when(categoryRepository.getCategories()).thenReturn(new HashSet<>(Arrays.asList(category1, category2, category3)));
        when(productRepository.getProducts()).thenReturn(new HashSet<>(Arrays.asList(product1, product2, product3)));

        // Начало теста
        CategoriesByCatalogResponce actualCategoriesByCatalogResponce = categoryService.getAllCategoriesByCatalog(catalogCode);
        assertEquals(expectedCategoriesByCatalogResponce, actualCategoriesByCatalogResponce);
        verify(categoryRepository).getCategories();
        verifyNoMoreInteractions(categoryRepository);
    }
}