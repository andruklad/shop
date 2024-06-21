package com.colvir.shop.service;

import com.colvir.shop.dto.CatalogRequest;
import com.colvir.shop.dto.CatalogWithCategories;
import com.colvir.shop.dto.CatalogsResponse;
import com.colvir.shop.expception.CatalogNotFoundException;
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

    public Catalog save(CatalogRequest catalogRequest) {
        Catalog catalog = catalogsMapper.catalogRequestToCatalog(catalogRequest);
        return catalogRepository.save(catalog);
    }

    public Catalog getByCode(String catalogCode) {

        Catalog catalog = catalogRepository.findByCode(catalogCode);

        if (catalog == null) {
            throw new CatalogNotFoundException(String.format("Каталог с кодом %s не найден", catalogCode));
        }

        return catalog;
    }

    public Catalog update(CatalogRequest catalogRequest) {

        // За одно, проверка наличия, чтобы сообщить об ошибке в случае отсутствия
        Catalog catalogByCode = getByCode(catalogRequest.getCode());
        Catalog catalogForUpdate = catalogsMapper.catalogRequestToCatalog(catalogRequest);
        // Преобразование кода в идентификатор
        catalogForUpdate.setId(catalogByCode.getId());

        return catalogRepository.save(catalogForUpdate);
    }

    public void deleteByCode(String catalogCode) {

        // Проверка наличия, чтобы сообщить об ошибке в случае отсутствия
        Catalog catalog = getByCode(catalogCode);
        catalogRepository.deleteById(catalog.getId());
    }

    private void addCatalog(String catalogCode, String catalogName) {

        CatalogRequest catalogRequest = new CatalogRequest(catalogCode, catalogName);
        save(catalogRequest);
    }

    public CatalogsResponse getAllCatalogs() {

        // Заполнение категории каждого каталога для объекта-ответа
        Set<CatalogWithCategories> catalogsWithCategories = catalogRepository.findAll().stream()
                .map(catalog -> new CatalogWithCategories(catalog.getCode(), categoryService.getAllCategoriesByCatalog(catalog.getCode()).getCategories()))
                .collect(Collectors.toSet());

        // Маппинг в объект-ответ
        return catalogsMapper.catalogsToCatalogsResponse(catalogsWithCategories);
    }

    public void loadTestData() {
        addCatalog("CatalogCode1", "CatalogName1");
        addCatalog("CatalogCode2", "CatalogName2");
        addCatalog("CatalogCode3", "CatalogName3");
    }
}
