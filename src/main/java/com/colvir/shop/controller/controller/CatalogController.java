package com.colvir.shop.controller.controller;

import com.colvir.shop.dto.CatalogsResponce;
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
    public Catalog save(@RequestBody Catalog catalog) {
        return catalogService.save(catalog);
    }

    @GetMapping("getByCode")
    public Catalog getByCode(@RequestParam String catalogCode) {

        return catalogService.getByCode(catalogCode);
    }

    @PutMapping("update")
    public Catalog update(@RequestBody Catalog catalog) {

        return catalogService.update(catalog);
    }

    @DeleteMapping("deleteByCode")
    public ResponseEntity deleteByCode(@RequestParam String catalogCode) {

        catalogService.deleteByCode(catalogCode);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("getAllCatalogs")
    public CatalogsResponce getAllCatalogs() {

        return catalogService.getAllCatalogs();
    }

    @GetMapping("loadTestData")
    public ResponseEntity loadTestData() {

        // Загрузка тестовых данных
        catalogService.loadTestData();

        return new ResponseEntity(HttpStatus.OK);
    }
}
