package com.colvir.shop.expception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {

        super(message);
    }
}
