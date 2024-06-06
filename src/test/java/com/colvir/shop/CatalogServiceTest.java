package com.colvir.shop;

import com.colvir.shop.dto.CatalogWithCategories;
import com.colvir.shop.dto.CatalogsResponce;
import com.colvir.shop.dto.CategoryWithProducts;
import com.colvir.shop.expception.CatalogNotFoundException;
import com.colvir.shop.mapper.CatalogsMapperImpl;
import com.colvir.shop.mapper.CategoriesByCatalogMapperImpl;
import com.colvir.shop.mapper.ProductsByCategoryMapperImpl;
import com.colvir.shop.model.Catalog;
import com.colvir.shop.model.Category;
import com.colvir.shop.model.Product;
import com.colvir.shop.repository.CatalogRepository;
import com.colvir.shop.repository.CategoryRepository;
import com.colvir.shop.repository.ProductRepository;
import com.colvir.shop.service.CatalogService;
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
        CatalogService.class,
        CategoryService.class,
        ProductService.class,
        CatalogsMapperImpl.class,
        CategoriesByCatalogMapperImpl.class,
        ProductsByCategoryMapperImpl.class
})
public class CatalogServiceTest {

    @Autowired
    CatalogService catalogService;

    @MockBean
    CatalogRepository catalogRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    ProductRepository productRepository;

    @Test
    void save_succes() {
        //Подготовка входных данных

        //Подготовка ожидаемого результата
        Catalog catalog = new Catalog("CatalogCode1", "CatalogName1");
        Catalog expectedCatalog = catalog;

        when(catalogRepository.save(catalog)).thenReturn(catalog);

        //Начало теста
        Catalog actualCatalog = catalogService.save(catalog);
        assertEquals(expectedCatalog, actualCatalog);
        verify(catalogRepository).save(any());
        verifyNoMoreInteractions(catalogRepository);
    }

    @Test
    void getByCode_success() {
        //Подготовка входных данных
        Catalog catalog = new Catalog("CatalogCode1", "CatalogName1");

        //Подготовка ожидаемого результата
        Catalog expectedCatalog = catalog;

        when(catalogRepository.getByCode(catalog.getCode())).thenReturn(catalog);

        //Начало теста
        Catalog actualCatalog = catalogService.getByCode(catalog.getCode());
        assertEquals(expectedCatalog, actualCatalog);
        verify(catalogRepository).getByCode(catalog.getCode());
        verifyNoMoreInteractions(catalogRepository);
    }

    @Test
    void getByCode_withException() {
        //Подготовка входных данных
        String catalogCode = "CatalogCode1";

        //Подготовка ожидаемого результата
        String expectedMessage = String.format("Каталог с кодом %s не найден", catalogCode);

        when(catalogRepository.getByCode(catalogCode)).thenReturn(null);

        //Начало теста
        Exception exception = assertThrows(CatalogNotFoundException.class, () -> catalogService.getByCode(catalogCode));
        assertEquals(expectedMessage, exception.getMessage());
        verify(catalogRepository).getByCode(catalogCode);
        verifyNoMoreInteractions(catalogRepository);
    }

    @Test
    void update_success() {
        //Подготовка входных данных
        Catalog catalog = new Catalog("CatalogCode1", "CatalogName1");

        //Подготовка ожидаемого результата
        Catalog expectedCatalog = catalog;

        when(catalogRepository.getByCode(catalog.getCode())).thenReturn(catalog);
        when(catalogRepository.update(catalog)).thenReturn(catalog);

        //Начало теста
        Catalog actualCatalog = catalogService.update(catalog);
        assertEquals(expectedCatalog, actualCatalog);
        verify(catalogRepository).getByCode(catalog.getCode());
        verify(catalogRepository).update(catalog);
        verifyNoMoreInteractions(catalogRepository);
    }

    @Test
    void getAllCatalogs_success() {
        //Подготовка входных данных

        //Подготовка ожидаемого результата
        Catalog catalog1 = new Catalog("CatalogCode1", "CatalogName1");
        Catalog catalog2 = new Catalog("CatalogCode2", "CatalogName1");

        Category category1 = new Category("CategoryCode1", "CategoryName1", "CatalogCode1");
        Category category2 = new Category("CategoryCode2", "CategoryName2", "CatalogCode2");

        Product product1 = new Product("001", "ProductName1", 10.0, "CategoryCode1");
        Product product2 = new Product("002", "ProductName2", 20.0, "CategoryCode2");

        CategoryWithProducts categoryWithProducts1 = new CategoryWithProducts(category1, new HashSet<>(List.of(product1)));
        CategoryWithProducts categoryWithProducts2 = new CategoryWithProducts(category2, new HashSet<>(List.of(product2)));

        CatalogWithCategories catalogWithCategories1 = new CatalogWithCategories(catalog1.getCode(), new HashSet<>(List.of(categoryWithProducts1)));
        CatalogWithCategories catalogWithCategories2 = new CatalogWithCategories(catalog2.getCode(), new HashSet<>(List.of(categoryWithProducts2)));

        CatalogsResponce expectedCatalogsResponce = new CatalogsResponce(new HashSet<>(Arrays.asList(catalogWithCategories1, catalogWithCategories2)));

        when(catalogRepository.getCatalogs()).thenReturn(new HashSet<>(Arrays.asList(catalog1, catalog2)));
        when(categoryRepository.getCategories()).thenReturn(new HashSet<>(Arrays.asList(category1, category2)));
        when(productRepository.getProducts()).thenReturn(new HashSet<>(Arrays.asList(product1, product2)));

        //Начало теста
        CatalogsResponce actualCatalogsResponce = catalogService.getAllCatalogs();
        assertEquals(expectedCatalogsResponce, actualCatalogsResponce);
        verify(catalogRepository).getCatalogs();
        verifyNoMoreInteractions(catalogRepository);
    }
}