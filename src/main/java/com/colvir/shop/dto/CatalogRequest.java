package com.colvir.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CatalogRequest {

    private String code;

    private String name;
}
