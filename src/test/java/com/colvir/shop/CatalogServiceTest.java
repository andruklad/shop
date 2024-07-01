package com.colvir.shop;

import com.colvir.shop.dto.CatalogRequest;
import com.colvir.shop.dto.CatalogWithCategories;
import com.colvir.shop.dto.CatalogsResponse;
import com.colvir.shop.dto.CategoryWithProducts;
import com.colvir.shop.expception.CatalogNotFoundException;
import com.colvir.shop.generator.CatalogDtoGenerator;
import com.colvir.shop.generator.CatalogGenerator;
import com.colvir.shop.mapper.CatalogsMapper;
import com.colvir.shop.mapper.CatalogsMapperImpl;
import com.colvir.shop.mapper.CategoriesMapperImpl;
import com.colvir.shop.mapper.ProductsMapperImpl;
import com.colvir.shop.model.Catalog;
import com.colvir.shop.model.Category;
import com.colvir.shop.model.Product;
import com.colvir.shop.repository.CatalogCacheRepository;
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

import java.util.ArrayList;
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
        CategoriesMapperImpl.class,
        ProductsMapperImpl.class
})
public class CatalogServiceTest {

    @Autowired
    CatalogService catalogService;

    @Autowired
    CatalogsMapper catalogsMapper;

    @MockBean
    CatalogRepository catalogRepository;

    @MockBean
    CatalogCacheRepository catalogCacheRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    ProductRepository productRepository;

    @Test
    void save_succes() {
        //Подготовка входных данных
        CatalogRequest catalogRequest = CatalogDtoGenerator.createCatalogRequestBuilder().build();
        Catalog catalog = catalogsMapper.catalogRequestToCatalog(catalogRequest);

        //Подготовка ожидаемого результата
        Catalog expectedCatalog = catalog;

        when(catalogRepository.save(catalog)).thenReturn(catalog);

        //Начало теста
        Catalog actualCatalog = catalogService.save(catalogRequest);
        assertEquals(expectedCatalog, actualCatalog);
        verify(catalogRepository).save(any());
        verifyNoMoreInteractions(catalogRepository);
    }

    @Test
    void getByCode_success() {
        //Подготовка входных данных
        Catalog catalog = CatalogGenerator.createCatalogBuilder().build();

        //Подготовка ожидаемого результата
        Catalog expectedCatalog = catalog;

        when(catalogRepository.findByCode(catalog.getCode())).thenReturn(catalog);

        //Начало теста
        Catalog actualCatalog = catalogService.getByCode(catalog.getCode());
        assertEquals(expectedCatalog, actualCatalog);
        verify(catalogRepository).findByCode(catalog.getCode());
        verifyNoMoreInteractions(catalogRepository);
    }

    @Test
    void getByCode_withException() {
        //Подготовка входных данных
        String catalogCode = CatalogGenerator.CATALOG_CODE;

        //Подготовка ожидаемого результата
        String expectedMessage = String.format("Каталог с кодом %s не найден", catalogCode);

        when(catalogRepository.findByCode(catalogCode)).thenReturn(null);

        //Начало теста
        Exception exception = assertThrows(CatalogNotFoundException.class, () -> catalogService.getByCode(catalogCode));
        assertEquals(expectedMessage, exception.getMessage());
        verify(catalogRepository).findByCode(catalogCode);
        verifyNoMoreInteractions(catalogRepository);
    }

    @Test
    void update_success() {
        //Подготовка входных данных
        CatalogRequest catalogRequest = CatalogDtoGenerator.createCatalogRequestBuilder().build();
        Catalog catalog = catalogsMapper.catalogRequestToCatalog(catalogRequest);

        //Подготовка ожидаемого результата
        Catalog expectedCatalog = catalog;

        when(catalogRepository.findByCode(catalog.getCode())).thenReturn(catalog);
        when(catalogRepository.save(catalog)).thenReturn(catalog);

        //Начало теста
        Catalog actualCatalog = catalogService.update(catalogRequest);
        assertEquals(expectedCatalog, actualCatalog);
        verify(catalogRepository).findByCode(catalog.getCode());
        verify(catalogRepository).save(catalog);
        verifyNoMoreInteractions(catalogRepository);
    }

    @Test
    void getAllCatalogs_success() {
        //Подготовка входных данных

        //Подготовка ожидаемого результата
        Catalog catalog1 = CatalogGenerator.createCatalogBuilder().build();
        Catalog catalog2 = CatalogGenerator.createCatalog2Builder().build();

        Category category1 = new Category(1, "CategoryCode1", "CategoryName1", catalog1.getId());
        Category category2 = new Category(2, "CategoryCode2", "CategoryName2", catalog2.getId());

        Product product1 = new Product(1, "001", "ProductName1", 10.0, 1);
        Product product2 = new Product(2,"002", "ProductName2", 20.0, 2);

        CategoryWithProducts categoryWithProducts1 = new CategoryWithProducts(category1, new HashSet<>(List.of(product1)));
        CategoryWithProducts categoryWithProducts2 = new CategoryWithProducts(category2, new HashSet<>(List.of(product2)));

        CatalogWithCategories catalogWithCategories1 = new CatalogWithCategories(catalog1.getCode(), new HashSet<>(List.of(categoryWithProducts1)));
        CatalogWithCategories catalogWithCategories2 = new CatalogWithCategories(catalog2.getCode(), new HashSet<>(List.of(categoryWithProducts2)));

        CatalogsResponse expectedCatalogsResponse = new CatalogsResponse(new HashSet<>(Arrays.asList(catalogWithCategories1, catalogWithCategories2)));

        when(catalogRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(catalog1, catalog2)));
        when(catalogRepository.findByCode(catalog1.getCode())).thenReturn(catalog1);
        when(catalogRepository.findByCode(catalog2.getCode())).thenReturn(catalog2);
        when(categoryRepository.findByCode("CategoryCode1")).thenReturn(category1);
        when(categoryRepository.findByCode("CategoryCode2")).thenReturn(category2);
        when(categoryRepository.findAllByCatalogId(catalog1.getId())).thenReturn(new ArrayList<>(List.of(category1)));
        when(categoryRepository.findAllByCatalogId(catalog2.getId())).thenReturn(new ArrayList<>(List.of(category2)));
        when(productRepository.findAllByCategoryId(category1.getId())).thenReturn(new ArrayList<>(List.of(product1)));
        when(productRepository.findAllByCategoryId(category2.getId())).thenReturn(new ArrayList<>(List.of(product2)));

        //Начало теста
        CatalogsResponse actualCatalogsResponse = catalogService.getAllCatalogs();
        assertEquals(expectedCatalogsResponse, actualCatalogsResponse);
        verify(catalogRepository).findAll();
        verify(catalogRepository).findByCode(catalog1.getCode());
        verify(catalogRepository).findByCode(catalog2.getCode());
        verifyNoMoreInteractions(catalogRepository);
    }
}
