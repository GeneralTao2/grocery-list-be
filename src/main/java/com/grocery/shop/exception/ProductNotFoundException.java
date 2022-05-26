package com.grocery.shop.exception;

import com.grocery.shop.model.Product;

import java.util.function.Supplier;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(long id)
    {
        super("Product " + id + " does not exist");
    }
}
