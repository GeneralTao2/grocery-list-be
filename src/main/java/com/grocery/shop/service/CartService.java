package com.grocery.shop.service;

import com.grocery.shop.dto.CartDto;
import com.grocery.shop.dto.CartItemDto;
import com.grocery.shop.dto.ProductWithQuantity;
import com.grocery.shop.dto.QuantityResponse;

import java.util.List;

public interface CartService {
    CartDto checkoutProducts(List<CartItemDto> cartItemDtoList);

    CartDto checkoutProductsForLoggedInUser();

    QuantityResponse addOneProductQuantityInCart(final ProductWithQuantity productWithQuantity);

    void editOneProductQuantityInCart(final ProductWithQuantity productWithQuantity);
}
