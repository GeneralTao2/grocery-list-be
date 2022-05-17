package com.grocery.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoShort {

    private Long productId;
    private String productImage;
    private String productName;
    private double productPrice;
    private double productRate;
}
