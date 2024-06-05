package com.colvir.shop.expception;

public class CatalogNotFoundException extends RuntimeException {

    public CatalogNotFoundException(String message) {

        super(message);
    }
}
