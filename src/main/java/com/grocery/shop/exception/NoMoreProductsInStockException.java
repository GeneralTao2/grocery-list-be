package com.grocery.shop.exception;

import lombok.Getter;

@Getter
public class NoMoreProductsInStockException extends RuntimeException {
    final int productQuantity;

    public NoMoreProductsInStockException(final int productQuantity) {
        super("No more products in stock");
        this.productQuantity = productQuantity;
    }
}
