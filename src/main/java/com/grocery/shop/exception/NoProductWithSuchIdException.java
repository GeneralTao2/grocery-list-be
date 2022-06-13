package com.grocery.shop.exception;

public class NoProductWithSuchIdException extends RuntimeException {
    public NoProductWithSuchIdException(Long id) {
        super("No product with id:" + id);
    }
}
