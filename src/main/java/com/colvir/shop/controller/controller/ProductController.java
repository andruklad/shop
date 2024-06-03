package com.colvir.shop.controller.controller;

import com.colvir.shop.dto.ProductsByCategoryResponce;
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

    @GetMapping("getByArticle")
    public Product getByArticle(@RequestParam String article) {

        return productService.getByArticle(article);
    }

    @DeleteMapping("deleteByArticle")
    public ResponseEntity deleteByArticle(@RequestParam String article) {

        productService.deleteByArticle(article);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("getAllProductsByCategory")
    public ProductsByCategoryResponce getAllProductsByCategory(@RequestParam String categoryCode) {
        return productService.getAllProductsByCategory(categoryCode);
    }

    @GetMapping("loadTestData")
    public ResponseEntity loadTestData() {

        // Загрузка тестовых данных
        productService.loadTestData();

        return new ResponseEntity(HttpStatus.OK);
    }
}
