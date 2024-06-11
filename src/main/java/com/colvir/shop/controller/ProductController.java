package com.colvir.shop.controller;

import com.colvir.shop.dto.ProductsByCategoryResponse;
import com.colvir.shop.model.Product;
import com.colvir.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("save")
    public Product save(@RequestBody Product product) {

        return productService.save(product);
    }

    @GetMapping("get-by-article")
    public Product getByArticle(@RequestParam String article) {

        return productService.getByArticle(article);
    }

    @PutMapping("update")
    public Product update(@RequestBody Product product) {

        return productService.update(product);
    }

    @DeleteMapping("delete-by-article")
    public ResponseEntity<?> deleteByArticle(@RequestParam String article) {

        productService.deleteByArticle(article);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("get-by-max-price")
    public Product getByMaxPrice() {

        return productService.getByMaxPrice();
    }

    @GetMapping("get-all-products-by-category")
    public ProductsByCategoryResponse getAllProductsByCategory(@RequestParam String categoryCode) {

        return productService.getAllProductsByCategory(categoryCode);
    }

    @GetMapping("load-test-data")
    public ResponseEntity<?> loadTestData() {

        // Загрузка тестовых данных
        productService.loadTestData();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
