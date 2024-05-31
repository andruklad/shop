package com.colvir.shop.controller.controller;

import com.colvir.shop.model.Catalog;
import com.colvir.shop.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @PostMapping("save")
    public Catalog save(@RequestBody Catalog catalog) {
        return catalogService.save(catalog);
    }
}
