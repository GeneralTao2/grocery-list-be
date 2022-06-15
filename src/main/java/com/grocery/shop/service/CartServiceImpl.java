package com.grocery.shop.service;

import com.grocery.shop.dto.CartDto;
import com.grocery.shop.dto.CartItemDto;
import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.dto.UserDetailsImpl;
import com.grocery.shop.exception.NotEnoughProductsInStockException;
import com.grocery.shop.exception.ProductNotFoundException;
import com.grocery.shop.exception.UserNotFoundException;
import com.grocery.shop.mapper.ProductMapper;
import com.grocery.shop.model.Cart;
import com.grocery.shop.model.Product;
import com.grocery.shop.model.User;
import com.grocery.shop.repository.CartRepository;
import com.grocery.shop.repository.ProductRepository;
import com.grocery.shop.repository.UserRepository;
import com.grocery.shop.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final Authentication authentication;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public CartDto checkoutProducts(List<CartItemDto> cartItemDtoList) {

        for (CartItemDto cartItemDto : cartItemDtoList) {
            Product product = productRepository.findById(cartItemDto.getProductDtoShort().getId())
                    .orElseThrow(
                            () -> new ProductNotFoundException(cartItemDto.getProductDtoShort().getId())
                    );
            if (product.getTotalCountInStock() < cartItemDto.getQuantity()) {
                throw new NotEnoughProductsInStockException(product);
            }
        }

        return new CartDto(cartItemDtoList, getTotalCost(cartItemDtoList));
    }

    private static double getTotalCost(List<CartItemDto> cartItemDtoList) {

        return cartItemDtoList.stream()
                .map(
                        cartItemDto -> cartItemDto.getProductDtoShort().getPrice()
                                * cartItemDto.getProductDtoShort().getSize()
                                * cartItemDto.getQuantity()
                )
                .reduce(Double::sum)
                .orElse(0.0);
    }

    @Override
    public CartDto checkoutProductsForLoggedInUser() {
        UserDetailsImpl userFromSecurity = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String email = userFromSecurity.getUsername();

        User userWithId = userRepository.findByEmail(email).orElseThrow(
                UserNotFoundException::new);

        Cart cart = cartRepository.findByUserId(userWithId.getId());

        return checkoutProducts(getCartItemListFromCart(cart));
    }

    private static List<CartItemDto> getCartItemListFromCart(Cart cart) {
        return cart.getCartItems()
                .entrySet()
                .stream()
                .map(
                        productIntegerEntry -> new CartItemDto(
                                ProductMapper.MAPPER.toDTOShort(productIntegerEntry.getKey()),
                                productIntegerEntry.getValue())
                )
                .collect(Collectors.toList());
    }

    @Override
    public void addItemsToCard(CartDto cartDto, String userName) {

        Long userId = userRepository.findByEmail(userName).orElseThrow(
                UserNotFoundException::new).getId();
        Cart cart = cartRepository.findByUserId(userId);

        for (int i = 0; i < cartDto.getCartItemList().size(); i++) {

            Long productID = cartDto.getCartItemList().get(i).getProductDtoShort().getId();

            if (cart.getCartItems().containsKey(productRepository.getById(productID))) {
                cart.getCartItems().put(
                        productRepository.getById(productID),
                        cartDto.getCartItemList().get(i).getQuantity() +
                                cart.getCartItems().get(productRepository.getById(productID)));
            } else {
                cart.getCartItems().put(
                        productRepository.getById(productID),
                        cartDto.getCartItemList().get(i).getQuantity());
            }
        }
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartItems(String userName) {

        Long userId = userRepository.findByEmail(userName).orElseThrow(
                UserNotFoundException::new).getId();

        if (Objects.isNull(userId) || Objects.isNull(cartRepository.findByUserId(userId))) {
            CartDto emptyCart = new CartDto();
            emptyCart.setCartItemList(new ArrayList<>());
            return emptyCart;
        }

        Cart cart = cartRepository.findByUserId(userId);

        CartDto cartDto = new CartDto();
        cartDto.setCartItemList(new ArrayList<>());
        double totalCost = 0.0;

        for (Map.Entry<Product, Integer> entry : cart.getCartItems().entrySet()) {
            CartItemDto cartItemDto = new CartItemDto();

            ProductDtoShort productDtoShort = ProductMapper.MAPPER.toDTOShort(entry.getKey());

            cartItemDto.setProductDtoShort(productDtoShort);
            cartItemDto.setQuantity(entry.getValue());

            totalCost = totalCost + entry.getKey().getPrice() * entry.getValue();

            cartDto.setTotalCost(totalCost);
            cartDto.getCartItemList().add(cartItemDto);
        }
        return cartDto;
    }
}
