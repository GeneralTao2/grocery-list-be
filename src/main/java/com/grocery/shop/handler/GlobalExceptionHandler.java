package com.grocery.shop.handler;

import com.grocery.shop.exception.ProductNotFoundException;
import com.grocery.shop.exception.ProductsNotFoundException;
import com.grocery.shop.exception.UserAlreadyExistsException;
import com.grocery.shop.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> onInvalidMethodArgument(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), 400));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> onUserAlreadyExists(UserAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(exception.getMessage(), 409));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> onException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(exception.getMessage(), 500));
    }

    @ExceptionHandler(ProductsNotFoundException.class)
    public ResponseEntity<ErrorResponse> onProductsNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage(), 404));
    }

    @Value
    @AllArgsConstructor
    private static class ErrorResponse {
        String message;
        int errorCode;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    protected void userNotFoundException() {

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    protected void productNotFoundException() {

    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(BadCredentialsException.class)
    protected void badCredentialsException() {

    }
}
