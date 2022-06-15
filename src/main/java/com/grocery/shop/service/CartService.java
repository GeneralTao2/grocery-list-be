package com.grocery.shop.service;

import com.grocery.shop.dto.CartDto;
import com.grocery.shop.dto.CartItemDto;

import java.util.List;

public interface CartService {
    CartDto checkoutProducts(List<CartItemDto> cartItemDtoList);

    CartDto checkoutProductsForLoggedInUser();

    void addItemsToCard(CartDto cartDto, String userName);

    CartDto getCartItems(String userName);
}
