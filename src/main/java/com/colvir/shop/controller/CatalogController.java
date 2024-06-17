package com.colvir.shop.controller;

import com.colvir.shop.dto.CatalogRequest;
import com.colvir.shop.dto.CatalogsResponse;
import com.colvir.shop.model.Catalog;
import com.colvir.shop.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @PostMapping("save")
    public Catalog save(@RequestBody CatalogRequest catalogRequest) {

        return catalogService.save(catalogRequest);
    }

    @GetMapping("get-by-code")
    public Catalog getByCode(@RequestParam String catalogCode) {

        return catalogService.getByCode(catalogCode);
    }

    @PutMapping("update")
    public Catalog update(@RequestBody CatalogRequest catalogRequest) {

        return catalogService.update(catalogRequest);
    }

    @DeleteMapping("delete-by-code")
    public ResponseEntity<?> deleteByCode(@RequestParam String catalogCode) {

        catalogService.deleteByCode(catalogCode);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("get-all-catalogs")
    public CatalogsResponse getAllCatalogs() {

        return catalogService.getAllCatalogs();
    }

    @GetMapping("load-test-data")
    public ResponseEntity<?> loadTestData() {

        // Загрузка тестовых данных
        catalogService.loadTestData();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
