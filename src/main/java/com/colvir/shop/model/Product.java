package com.colvir.shop.model;

import lombok.Data;

@Data
public class Product {

    private final String article;

    private final String name;

    private final Double price;

    private final String categoryCode;
}
