package com.colvir.shop.service;

import com.colvir.shop.dto.ProductRequest;
import com.colvir.shop.dto.ProductsByCategoryResponse;
import com.colvir.shop.expception.CategoryNotFoundException;
import com.colvir.shop.expception.ProductNotFoundException;
import com.colvir.shop.mapper.ProductsMapper;
import com.colvir.shop.model.Category;
import com.colvir.shop.model.Product;
import com.colvir.shop.repository.CategoryRepository;
import com.colvir.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductsMapper productsMapper;

    private Integer getCategoryIdByCategoryCode(String categoryCode) {

        Category category = categoryRepository.findByCode(categoryCode);

        if (category == null) {
            throw new CategoryNotFoundException(String.format("Категория с кодом %s не найдена", categoryCode));
        }

        return category.getId();
    }

    private void fillCategoryIdByCategoryCode(Product product, String categoryCode) {

        Integer categoryId = getCategoryIdByCategoryCode(categoryCode);
        product.setCategoryId(categoryId);
    }

    private void fillProductIdByCode(Product product) {

        Product productByArticle = getByArticle(product.getArticle());
        product.setId(productByArticle.getId());
    }

    public Product save(ProductRequest productRequest) {

        Product product = productsMapper.productRequestToProduct(productRequest);
        fillCategoryIdByCategoryCode(product, productRequest.getCategoryCode());

        return productRepository.save(product);
    }

    public Product getByArticle(String article) {

        Product product = productRepository.findByArticle(article);//productRepository.getByArticle(article);

        if (product == null) {
            throw new ProductNotFoundException(String.format("Продукт с артиклем %s не найден", article));
        }

        return product;
    }

    public Product update(ProductRequest productRequest) {

        Product product = productsMapper.productRequestToProduct(productRequest);
        fillProductIdByCode(product);
        fillCategoryIdByCategoryCode(product, productRequest.getCategoryCode());

        return productRepository.save(product); //productRepository.update(product);
    }

    public void deleteByArticle(String article) {

        // Проверка наличия, чтобы сообщить об ошибке в случае отсутствия
        Product product = getByArticle(article);

        productRepository.deleteById(product.getId());
    }

    public Product getByMaxPrice() {

        return productRepository.findFirstByOrderByPriceDesc();
    }

    public ProductsByCategoryResponse getAllProductsByCategory(String categoryCode) {

        Integer categoryId = getCategoryIdByCategoryCode(categoryCode);
        Set<Product> products = new HashSet<>(productRepository.findAllByCategoryId(categoryId));

        return productsMapper.productsToProductsByCategoryResponse(products);
    }

    private void addProduct(String article, String name, Double price, String categoryCode) {

        ProductRequest productRequest = new ProductRequest(article, name, price, categoryCode);
        save(productRequest);
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
