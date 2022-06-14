package com.grocery.shop.exception;

import lombok.Getter;

@Getter
public class ProductQuantityIsBiggerThenInDbException extends RuntimeException {
    final int productQuantity;

    public ProductQuantityIsBiggerThenInDbException(final int productQuantity) {
        super("Incoming product quantity is bigger then in DB");
        this.productQuantity = productQuantity;
    }
}
