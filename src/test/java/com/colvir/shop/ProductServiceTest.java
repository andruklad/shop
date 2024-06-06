package com.colvir.shop;

import com.colvir.shop.dto.ProductsByCategoryResponce;
import com.colvir.shop.expception.ProductNotFoundException;
import com.colvir.shop.mapper.ProductsByCategoryMapper;
import com.colvir.shop.model.Product;
import com.colvir.shop.repository.ProductRepository;
import com.colvir.shop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ProductService.class
})
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    ProductsByCategoryMapper productsByCategoryMapper;

    @Test void save_success() {
        //Подготовка входных данных
        Product product = new Product("001", "ProductName1", 10.0, "CategoryCode1");

        //Подготовка ожидаемого результата
        Product expectedProduct = product;

        when(productRepository.save(product)).thenReturn(product);

        //Начало теста
        Product actualProduct = productService.save(product);
        assertEquals(expectedProduct, actualProduct);
        verify(productRepository).save(any());
        verifyNoMoreInteractions(productRepository);
    }

    @Test void getByArticle_success() {
        // Подготовка входных данных
        Product product = new Product("001", "ProductName1", 10.0, "CategoryCode1");

        // Подготовка ожидаемого результата
        Product expectedProduct = product;

        when(productRepository.save(product)).thenReturn(product);
        when(productRepository.getByArticle(product.getArticle())).thenReturn(product);

        // Начало теста
        productService.save(product);
        Product actualProduct = productService.getByArticle(product.getArticle());
        assertEquals(expectedProduct, actualProduct);
        verify(productRepository).save(product);
        verify(productRepository).getByArticle(product.getArticle());
        verifyNoMoreInteractions(productRepository);
    }

    @Test void getByArticle_withException() {
        // Подготовка входных данных
        Product product = new Product("001", "ProductName1", 10.0, "CategoryCode1");

        // Подготовка ожидаемого результата
        String article = product.getArticle();
        String expectedMessage = String.format("Продукт с артиклем %s не найден", article);

        when(productRepository.getByArticle(article)).thenReturn(null);

        // Начало теста
        Exception exception = assertThrows(ProductNotFoundException.class, () -> productService.getByArticle(article));
        assertEquals(expectedMessage, exception.getMessage());
        verify(productRepository).getByArticle(article);
        verifyNoMoreInteractions(productRepository);
    }

    @Test void update_success() {
        //Подготовка входных данных
        Product product = new Product("001", "ProductName1", 10.0, "CategoryCode1");

        //Подготовка ожидаемого результата
        Product expectedProduct = new Product("001", "ProductName2", 20.0, "CategoryCode1");

        when(productRepository.getByArticle(product.getArticle())).thenReturn(expectedProduct);
        when(productRepository.update(product)).thenReturn(expectedProduct);

        //Начало теста
        Product actualProduct = productService.update(product);
        assertEquals(expectedProduct, actualProduct);
        verify(productRepository).getByArticle(any());
        verify(productRepository).update(any());
        verifyNoMoreInteractions(productRepository);
    }

    @Test void deleteByArticle_success() {
        // Подготовка входных данных
        Product product = new Product("001", "ProductName1", 10.0, "CategoryCode1");

        // Подготовка ожидаемого результата
        String article = product.getArticle();

        when(productRepository.getByArticle(article)).thenReturn(product);

        // Начало теста
        productService.save(product);
        productService.deleteByArticle(product.getArticle());
        verify(productRepository).save(product);
        verify(productRepository).getByArticle(article);
        verify(productRepository).deleteByArticle(product.getArticle());
        verifyNoMoreInteractions(productRepository);
    }

    @Test void getByMaxPrice_success() {
        // Подготовка входных данных
        Product product1 = new Product("001", "ProductName1", 10.0, "CategoryCode1");
        Product product2 = new Product("002", "ProductName2", 20.0, "CategoryCode1");
        Product product3 = new Product("003", "ProductName3", 30.0, "CategoryCode1");

        // Подготовка ожидаемого результата
        Product expectedProduct = product3;

        when(productRepository.getProducts()).thenReturn(new HashSet<>(Arrays.asList(product1, product2, product3)));

        // Начало теста
        Product actualProduct = productService.getByMaxPrice();
        assertEquals(expectedProduct, actualProduct);
        verify(productRepository).getProducts();
        verifyNoMoreInteractions(productRepository);
    }

    @Test void getAllProductsByCategory_success() {
        // Подготовка входных данных
        Product product1 = new Product("001", "ProductName1", 10.0, "CategoryCode1");
        Product product2 = new Product("002", "ProductName2", 20.0, "CategoryCode1");
        Product product3 = new Product("003", "ProductName3", 30.0, "CategoryCode2");

        // Подготовка ожидаемого результата
        Set<Product> expectedProducts = new HashSet<>(Arrays.asList(product1, product2));
        ProductsByCategoryResponce expectedProductsByCategoryResponce = new ProductsByCategoryResponce(expectedProducts);

        when(productRepository.getProducts()).thenReturn(new HashSet<>(Arrays.asList(product1, product2, product3)));
        when(productsByCategoryMapper.productsToProductsByCategoryResponce(expectedProducts)).thenReturn(expectedProductsByCategoryResponce);

        // Начало теста
        ProductsByCategoryResponce actualProductsByCategoryResponce = productService.getAllProductsByCategory("CategoryCode1");
        assertEquals(expectedProductsByCategoryResponce, actualProductsByCategoryResponce);
        verify(productRepository).getProducts();
        verifyNoMoreInteractions(productRepository);
    }

}
