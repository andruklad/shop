package com.colvir.shop.controller;

import com.colvir.shop.dto.CategoriesByCatalogResponse;
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

    @GetMapping("get-by-code")
    public Category getByCode(@RequestParam String categoryCode) {

        return categoryService.getByCode(categoryCode);
    }

    @PutMapping("update")
    public Category update(@RequestBody Category category) {
        return categoryService.update(category);
    }

    @DeleteMapping("delete-by-code")
    public ResponseEntity deleteByCode(@RequestParam String categoryCode) {

        categoryService.deleteByCode(categoryCode);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("get-all-categories-by-catalog")
    public CategoriesByCatalogResponse getAllCategoriesByCatalog(@RequestParam String catalogCode) {

        return categoryService.getAllCategoriesByCatalog(catalogCode);
    }

    @GetMapping("load-test-data")
    public ResponseEntity loadTestData() {

        // Загрузка тестовых данных
        categoryService.loadTestData();

        return new ResponseEntity(HttpStatus.OK);
    }
}
