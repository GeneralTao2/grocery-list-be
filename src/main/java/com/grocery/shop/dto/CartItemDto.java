package com.grocery.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    @NotNull
    private ProductDtoShort productDtoShort;

    @NotNull
    private int quantity;
}
