package com.grocery.shop.controller;

import com.grocery.shop.dto.ProductQuantity;
import com.grocery.shop.dto.QuantityResponse;
import com.grocery.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/user/cart/one")
    QuantityResponse addOne(@RequestBody @Validated ProductQuantity productQuantity) {
        return cartService.addOne(productQuantity);
    }

    @PostMapping(value = "/cart/checkout")
    public ResponseEntity<CartDto> checkoutProductList (@RequestBody List<@Valid CartItemDto> cartItemDtoList) {
        CartDto cartDto = cartService.checkoutProducts(cartItemDtoList);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @GetMapping(value = "/user/cart/checkout")
    public ResponseEntity<CartDto> checkoutProductListForLoggedInUser () {
        CartDto cartDto = cartService.checkoutProductsForLoggedInUser();

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}
