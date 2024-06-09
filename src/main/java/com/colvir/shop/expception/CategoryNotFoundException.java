package com.colvir.shop.expception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {

        super(message);
    }
}
