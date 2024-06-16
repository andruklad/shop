package com.colvir.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductRequest {

    private String article;

    private String name;

    private Double price;

    private String categoryCode;
}
