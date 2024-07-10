package com.colvir.shop.generator;

import com.colvir.shop.model.Catalog;

public class CatalogGenerator {

    public static final Integer CATALOG_ID = 1;
    public static final String CATALOG_CODE = "CatalogCode1";
    public static final String CATALOG_NAME = "CatalogName1";
    public static final Integer CATALOG_ID_2 = 2;
    public static final String CATALOG_CODE_2 = "CatalogCode2";
    public static final String CATALOG_NAME_2 = "CatalogName2";

    public static Catalog.CatalogBuilder createCatalogBuilder() {

        return Catalog.builder()
                .setId(CATALOG_ID)
                .setCode(CATALOG_CODE)
                .setName(CATALOG_NAME);
    }

    public static Catalog.CatalogBuilder createCatalog2Builder() {

        return Catalog.builder()
                .setId(CATALOG_ID_2)
                .setCode(CATALOG_CODE_2)
                .setName(CATALOG_NAME_2);
    }
}
