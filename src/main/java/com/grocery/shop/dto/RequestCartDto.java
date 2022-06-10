package com.grocery.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCartDto {
    private Long id;
    @NotNull
    private Long productId;
    @NotNull
    private Long quantity;
}
