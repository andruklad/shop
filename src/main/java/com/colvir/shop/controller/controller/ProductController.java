package com.colvir.shop.controller.controller;

import com.colvir.shop.dto.ProductsByCategoryResponce;
import com.colvir.shop.model.Product;
import com.colvir.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("getAllProductsByCategory")
    public ProductsByCategoryResponce getAllProductsByCategory(@RequestParam String categoryCode) {
        return productService.getAllProductsByCategory(categoryCode);
    }
}