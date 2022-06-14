package com.grocery.shop.exception;

import com.grocery.shop.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotEnoughProductsInStockException extends RuntimeException {
    public NotEnoughProductsInStockException(String message) {
        super(message);
    }

    public NotEnoughProductsInStockException(Product product) {
        super(String.format("Not enough products with name %s in stock. Remaining amount: %d", product.getName(),
                product.getTotalCountInStock()));
    }
}
