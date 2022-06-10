package com.grocery.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductCategoryNotFound extends RuntimeException {
    public ProductCategoryNotFound(String message) {
        super(message);
    }
}
