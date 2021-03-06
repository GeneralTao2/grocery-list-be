package com.grocery.shop.dto;

import com.grocery.shop.model.ProductCategory;
import com.grocery.shop.model.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDtoFull {

    private Long id;
    private String image;
    private String name;
    private double price;
    private double rate;
    private String description;
    private Type type;
    private int size;
    private ProductCategory category;
}
