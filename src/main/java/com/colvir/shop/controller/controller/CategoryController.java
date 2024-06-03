package com.colvir.shop.controller.controller;

import com.colvir.shop.dto.CategoriesByCatalogResponce;
import com.colvir.shop.model.Category;
import com.colvir.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("save")
    public Category save(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @GetMapping("getAllCategoriesByCatalog")
    public CategoriesByCatalogResponce getAllCategoriesByCatalog(@RequestParam String catalogCode) {
        return categoryService.getAllCategoriesByCatalog(catalogCode);
    }

    @GetMapping("loadTestData")
    public ResponseEntity loadTestData() {

        // Загрузка тестовых данных
        categoryService.loadTestData();

        return new ResponseEntity(HttpStatus.OK);
    }
}
