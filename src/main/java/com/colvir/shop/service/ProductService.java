package com.colvir.shop.service;

import com.colvir.shop.dto.ProductsByCategoryResponce;
import com.colvir.shop.mapper.ProductsByCategoryMapper;
import com.colvir.shop.model.Product;
import com.colvir.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductsByCategoryMapper productsByCategoryMapper;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product getByArticle(String article) {
        return productRepository.getByArticle(article);
    }

    public void deleteByArticle(String article) {
        productRepository.deleteByArticle(article);
    }

    public ProductsByCategoryResponce getAllProductsByCategory(String categoryCode) {
        Set<Product> products = productRepository.getProducts().stream()
                .filter(product -> product.getCategoryCode().equals(categoryCode))
                .collect(Collectors.toSet());

        return productsByCategoryMapper.productsToProductsByCategoryResponce(products);
    }

    private void addProduct(String article, String name, Double price, String categoryCode) {
        Product product = new Product(article, name, price, categoryCode);
        save(product);
    }

    public void loadTestData() {
        // CategoryCode1
        addProduct("001", "ProductName1", 10.0, "CategoryCode1");
        addProduct("002", "ProductName2", 20.0, "CategoryCode1");
        // CategoryCode2
        addProduct("003", "ProductName3", 30.0, "CategoryCode2");
        addProduct("004", "ProductName4", 40.0, "CategoryCode2");
        // CategoryCode3
        addProduct("005", "ProductName5", 50.0, "CategoryCode3");
        addProduct("006", "ProductName6", 60.0, "CategoryCode3");
        // CategoryCode4
        addProduct("007", "ProductName7", 70.0, "CategoryCode4");
        addProduct("008", "ProductName8", 80.0, "CategoryCode4");
        // CategoryCode5
        addProduct("009", "ProductName9", 90.0, "CategoryCode5");
        addProduct("010", "ProductName10", 100.0, "CategoryCode5");
        // CategoryCode6
        addProduct("011", "ProductName11", 110.0, "CategoryCode6");
        addProduct("012", "ProductName12", 120.0, "CategoryCode6");
    }
}
