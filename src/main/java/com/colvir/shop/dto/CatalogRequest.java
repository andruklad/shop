package com.colvir.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class CatalogRequest {

    private String code;

    private String name;
}
