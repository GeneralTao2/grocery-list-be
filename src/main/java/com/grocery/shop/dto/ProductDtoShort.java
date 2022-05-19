package com.grocery.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoShort {

    private Long id;
    private String image;
    private String name;
    private double price;
    private double rate;
}
