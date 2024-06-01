package com.colvir.shop.controller.controller;

import com.colvir.shop.model.Category;
import com.colvir.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("save")
    public Category save(@RequestBody Category category) {
        return categoryService.save(category);
    }
}
