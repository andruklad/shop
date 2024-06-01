package com.colvir.shop.controller.controller;

import com.colvir.shop.model.Product;
import com.colvir.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("save")
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }
}
