package com.grocery.shop.service;

import com.grocery.shop.dto.CartDto;
import com.grocery.shop.dto.CartItemDto;
import com.grocery.shop.dto.ProductWithQuantity;
import com.grocery.shop.dto.QuantityResponse;
import com.grocery.shop.dto.UserDetailsImpl;
import com.grocery.shop.exception.NoMoreProductsInStockException;
import com.grocery.shop.exception.NotEnoughProductsInStockException;
import com.grocery.shop.exception.ProductNotFoundException;
import com.grocery.shop.exception.ProductsNotFoundException;
import com.grocery.shop.exception.UserNotFoundException;
import com.grocery.shop.mapper.ProductMapper;
import com.grocery.shop.model.Cart;
import com.grocery.shop.model.Product;
import com.grocery.shop.model.User;
import com.grocery.shop.repository.CartRepository;
import com.grocery.shop.repository.ProductRepository;
import com.grocery.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

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
    public QuantityResponse addOne(final ProductWithQuantity productWithQuantityFromReques) {
        final UserDetailsImpl userFromSecurity = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        final String email = userFromSecurity.getUsername();

        final User userWithId = userRepository.findByEmail(email).orElseThrow(
                UserNotFoundException::new
        );
        final Cart cart = cartRepository.findByUserId(userWithId.getId());
        final Product product = productRepository.findById(productWithQuantityFromReques.getProductId())
                .orElseThrow(() -> new ProductsNotFoundException("Product not found"));

        final Map<Product, Integer> cartItems = cart.getCartItems();
        final int additionalProductQuantityFromRequest = productWithQuantityFromReques.getQuantity();

        if (cartItems.containsKey(product)) {
            final int productQuantityFromStock = product.getTotalCountInStock();
            final int productQuantityFromCart = cart.getCartItems().get(product);
            final int totalProductQuantityInCart = productQuantityFromCart + additionalProductQuantityFromRequest;

            if (totalProductQuantityInCart > productQuantityFromStock) {
                throw new NoMoreProductsInStockException(productQuantityFromStock);
            }

            cartItems.replace(product, totalProductQuantityInCart);
        } else {
            cartItems.put(product, additionalProductQuantityFromRequest);
        }

        cartRepository.save(cart);

        final int cartSize = cartItems.size();
        return new QuantityResponse(HttpStatus.OK.value(), cartSize);
    }
}
