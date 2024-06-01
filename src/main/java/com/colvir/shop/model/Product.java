package com.colvir.shop.model;

import lombok.Data;

@Data
public class Product {

    private String article;

    private String name;

    private Double price;

    private String categoryCode;
}
