package com.grocery.shop.handler;

import com.grocery.shop.dto.ErrorResponse;
import com.grocery.shop.dto.QuantityResponse;
import com.grocery.shop.exception.NoMoreProductsInStockException;
import com.grocery.shop.exception.NotEnoughProductsInStockException;
import com.grocery.shop.exception.PageNotFoundException;
import com.grocery.shop.exception.ProductCategoryNotFound;
import com.grocery.shop.exception.ProductNotFoundException;
import com.grocery.shop.exception.ProductQuantityIsBiggerThenInDbException;
import com.grocery.shop.exception.ProductsNotFoundException;
import com.grocery.shop.exception.UserAlreadyExistsException;
import com.grocery.shop.exception.UserNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    protected final Log logger = LogFactory.getLog(getClass());

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> onInvalidMethodArgument(MethodArgumentNotValidException exception) {
        String errorMessage = "Validation failed for unknown reason";

        if (exception.getBindingResult().hasFieldErrors("email")) {
            errorMessage = "Validation failed for email field. Please enter valid email address: it should not be " +
                    "empty and should" +
                    " contain " +
                    "@ symbol, dot (.) symbol and at least 2 symbols for domain part";
        } else if (exception.getBindingResult().hasFieldErrors("password")) {
            errorMessage = "Validation failed for password field. Password should be alphanumeric with size between 5" +
                    " and 10 symbols";
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage, 400));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> onUserAlreadyExists(UserAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(exception.getMessage(), 409));
    }

    //@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> onException(Exception exception) {
        logger.info(exception.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Unknown error occurred", 500));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> onMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        if (e.getMostSpecificCause().getClass() == ProductCategoryNotFound.class) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMostSpecificCause().getMessage(), 404));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse("Bad Request: [" + e.getMostSpecificCause().getMessage() + "]", 400));
    }

    @ExceptionHandler(ProductsNotFoundException.class)
    public ResponseEntity<ErrorResponse> onProductsNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage(), 404));
    }

    @ExceptionHandler(PageNotFoundException.class)
    public ResponseEntity<ErrorResponse> onPagesNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage(), 404));
    }

    @ExceptionHandler(NotEnoughProductsInStockException.class)
    public ResponseEntity<ErrorResponse> onNotEnoughProductsInStock(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage(), 404));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    protected void userNotFoundException() {

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    protected ErrorResponse productNotFoundException(ProductNotFoundException e) {
        return new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(BadCredentialsException.class)
    protected void badCredentialsException() {

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoMoreProductsInStockException.class)
    protected QuantityResponse noMoreProductsInStockException(NoMoreProductsInStockException e) {
        return new QuantityResponse(452, e.getProductQuantity());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductQuantityIsBiggerThenInDbException.class)
    protected QuantityResponse productQuantityIsBiggerThenInDbException(ProductQuantityIsBiggerThenInDbException e) {
        return new QuantityResponse(453, e.getProductQuantity());
    }

}
