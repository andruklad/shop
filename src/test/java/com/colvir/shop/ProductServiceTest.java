package com.colvir.shop;

import com.colvir.shop.dto.ProductRequest;
import com.colvir.shop.dto.ProductsByCategoryResponse;
import com.colvir.shop.expception.ProductNotFoundException;
import com.colvir.shop.mapper.ProductsMapper;
import com.colvir.shop.mapper.ProductsMapperImpl;
import com.colvir.shop.model.Category;
import com.colvir.shop.model.Product;
import com.colvir.shop.repository.CategoryRepository;
import com.colvir.shop.repository.ProductRepository;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ProductService.class,
    ProductsMapperImpl.class
})
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    ProductsMapper productsMapper;

    @Test void save_success() {
        //Подготовка входных данных
        ProductRequest productRequest = new ProductRequest("001", "ProductName1", 10.0, "CategoryCode1");
        Product product = productsMapper.productRequestToProduct(productRequest);
        product.setCategoryId(1);

        //Подготовка ожидаемого результата
        Product expectedProduct = product;
        Category category = new Category(1, "CategoryCode1", "CategoryName1", 1);

        when(productRepository.save(product)).thenReturn(product);
        when(categoryRepository.findByCode(category.getCode())).thenReturn(category);

        //Начало теста
        Product actualProduct = productService.save(productRequest);
        assertEquals(expectedProduct, actualProduct);
        verify(productRepository).save(any());
        verifyNoMoreInteractions(productRepository);
    }

    @Test void getByArticle_success() {
        // Подготовка входных данных
        Product product = new Product(1, "001", "ProductName1", 10.0, 1);

        // Подготовка ожидаемого результата
        Product expectedProduct = product;

        when(productRepository.save(product)).thenReturn(product);
        when(productRepository.findByArticle(product.getArticle())).thenReturn(product);

        // Начало теста
        Product actualProduct = productService.getByArticle(product.getArticle());
        assertEquals(expectedProduct, actualProduct);
        verify(productRepository).findByArticle(product.getArticle());
        verifyNoMoreInteractions(productRepository);
    }

    @Test void getByArticle_withException() {
        // Подготовка входных данных
        Product product = new Product(1, "001", "ProductName1", 10.0, 1);

        // Подготовка ожидаемого результата
        String article = product.getArticle();
        String expectedMessage = String.format("Продукт с артиклем %s не найден", article);

        when(productRepository.findByArticle(article)).thenReturn(null);

        // Начало теста
        Exception exception = assertThrows(ProductNotFoundException.class, () -> productService.getByArticle(article));
        assertEquals(expectedMessage, exception.getMessage());
        verify(productRepository).findByArticle(article);
        verifyNoMoreInteractions(productRepository);
    }

    @Test void update_success() {
        //Подготовка входных данных
        ProductRequest productRequest = new ProductRequest("001", "ProductName2", 20.0, "CategoryCode2");
        Product productFromRequest = new Product(1, "001", "ProductName2", 20.0, 2);
        Product productFromRepository = new Product(1, "001", "ProductName1", 10.0, 1);

        //Подготовка ожидаемого результата
        Product expectedProduct = productFromRequest;
        Category expectedCategory = new Category(2, "CategoryCode2", "CategoryName2", 2);

        when(productRepository.findByArticle(productFromRepository.getArticle())).thenReturn(productFromRepository);
        when(categoryRepository.findByCode(expectedCategory.getCode())).thenReturn(expectedCategory);
        when(productRepository.save(expectedProduct)).thenReturn(expectedProduct);

        //Начало теста
        Product actualProduct = productService.update(productRequest);
        assertEquals(expectedProduct, actualProduct);
        verify(productRepository).findByArticle(any());
        verify(productRepository).save(any());
        verifyNoMoreInteractions(productRepository);
    }

    @Test void deleteByArticle_success() {
        // Подготовка входных данных
        Product product = new Product(1, "ProductCode1", "ProductName1", 10.0, 1);

        // Подготовка ожидаемого результата
        String article = product.getArticle();

        when(productRepository.findByArticle(article)).thenReturn(product);

        // Начало теста
        productService.deleteByArticle(product.getArticle());
        verify(productRepository).findByArticle(article);
        verify(productRepository).deleteById(product.getId());
        verifyNoMoreInteractions(productRepository);
    }

    @Test void getByMaxPrice_success() {
        // Подготовка входных данных
        Product product1 = new Product(1, "001", "ProductName1", 10.0, 1);

        // Подготовка ожидаемого результата
        Product expectedProduct = product1;

        when(productRepository.findFirstByOrderByPriceDesc()).thenReturn(product1);

        // Начало теста
        Product actualProduct = productService.getByMaxPrice();
        assertEquals(expectedProduct, actualProduct);
        verify(productRepository).findFirstByOrderByPriceDesc();
        verifyNoMoreInteractions(productRepository);
    }

    @Test void getAllProductsByCategory_success() {
        // Подготовка входных данных
        Product product1 = new Product(1, "001", "ProductName1", 10.0, 1);
        Product product2 = new Product(2, "002", "ProductName2", 20.0, 1);

        // Подготовка ожидаемого результата
        Set<Product> expectedProducts = new HashSet<>(Arrays.asList(product1, product2));
        ProductsByCategoryResponse expectedProductsByCategoryResponse = new ProductsByCategoryResponse(expectedProducts);
        Category category = new Category(1, "CategoryCode1", "CategoryName1", 1);

        when(categoryRepository.findByCode(category.getCode())).thenReturn(category);
        when(productRepository.findAllByCategoryId(category.getId())).thenReturn(new ArrayList<>(Arrays.asList(product1, product2)));

        // Начало теста
        ProductsByCategoryResponse actualProductsByCategoryResponse = productService.getAllProductsByCategory(category.getCode());
        assertEquals(expectedProductsByCategoryResponse, actualProductsByCategoryResponse);
        verify(productRepository).findAllByCategoryId(category.getId());
        verifyNoMoreInteractions(productRepository);
    }
}
