package com.colvir.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryRequest {

    private String code;

    private String name;

    private String catalogCode;
}
