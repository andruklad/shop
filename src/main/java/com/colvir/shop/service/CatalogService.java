package com.colvir.shop.service;

import com.colvir.shop.dto.CatalogWithCategories;
import com.colvir.shop.dto.CatalogsResponce;
import com.colvir.shop.mapper.CatalogsMapper;
import com.colvir.shop.model.Catalog;
import com.colvir.shop.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;

    private final CategoryService categoryService;

    private final CatalogsMapper catalogsMapper;

    public Catalog save(Catalog catalog) {
        return catalogRepository.save(catalog);
    }

    private void addCatalog(String catalogCode, String catalogName) {
        Catalog catalog = new Catalog(catalogCode, catalogName);
        save(catalog);
    }

    public CatalogsResponce getAllCatalogs() {

        // Заполнение категории каждого каталога для объекта-ответа
        Set<CatalogWithCategories> catalogsWithCategories = catalogRepository.getCatalogs().stream()
                .map(catalog -> new CatalogWithCategories(catalog.getCode(), categoryService.getAllCategoriesByCatalog(catalog.getCode()).getCategories()))
                .collect(Collectors.toSet());

        // Маппинг в объект-ответ
        return catalogsMapper.catalogsToCatalogsResponce(catalogsWithCategories);
    }

    public void loadTestData() {
        addCatalog("CatalogCode1", "CatalogName1");
        addCatalog("CatalogCode2", "CatalogName2");
        addCatalog("CatalogCode3", "CatalogName3");
    }
}
