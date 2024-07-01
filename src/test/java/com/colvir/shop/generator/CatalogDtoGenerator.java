package com.colvir.shop.generator;

import com.colvir.shop.dto.CatalogRequest;

public class CatalogDtoGenerator {

    public static CatalogRequest.CatalogRequestBuilder createCatalogRequestBuilder() {

        return CatalogRequest.builder()
                .setCode(CatalogGenerator.CATALOG_CODE)
                .setName(CatalogGenerator.CATALOG_NAME);
    }
}
