package com.grocery.shop.service;

import com.grocery.shop.dto.CartDto;
import com.grocery.shop.dto.CartItemDto;
import com.grocery.shop.dto.UserDetailsImpl;
import com.grocery.shop.exception.NotEnoughProductsInStockException;
import com.grocery.shop.exception.ProductNotFoundException;
import com.grocery.shop.exception.UserNotFoundException;
import com.grocery.shop.mapper.ProductMapper;
import com.grocery.shop.model.Cart;
import com.grocery.shop.model.Product;
import com.grocery.shop.model.ProductCategory;
import com.grocery.shop.model.Role;
import com.grocery.shop.model.Type;
import com.grocery.shop.model.User;
import com.grocery.shop.repository.CartRepository;
import com.grocery.shop.repository.ProductRepository;
import com.grocery.shop.repository.UserRepository;
import com.grocery.shop.security.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void whenCheckoutCartItemListWithSufficientQuantity_ThenReturnCorrespondingDto(){
        final List<Product> productList = Arrays.asList(
                new Product(1L, "image1", "name1", 50, 4, "Description1", 5, Type.WEIGHABLE, 1,
                            ProductCategory.FRUITS, 10),
                new Product(2L, "image2", "name2", 40, 3, "Description2", 6, Type.WEIGHABLE, 1,
                            ProductCategory.FRUITS, 10)
        );

        final List<CartItemDto> cartItemDtoList = Arrays.asList(
                new CartItemDto(ProductMapper.MAPPER.toDTOShort(productList.get(0)), 5),
                new CartItemDto(ProductMapper.MAPPER.toDTOShort(productList.get(1)), 8)
        );

        final CartDto expectedDto = new CartDto(cartItemDtoList, 570);

        final Long productId1 = cartItemDtoList.get(0).getProductDtoShort().getId();
        final Long productId2 = cartItemDtoList.get(1).getProductDtoShort().getId();

        when(productRepository.findById(productId1)).thenReturn(
                Optional.ofNullable(productList.get(0)));

        when(productRepository.findById(productId2)).thenReturn(
                Optional.ofNullable(productList.get(1)));

        final CartDto actualDto = cartService.checkoutProducts(cartItemDtoList);

        assertThat(actualDto)
                .overridingErrorMessage("Actual CartDto is not equal to expected one")
                .isEqualTo(expectedDto);
    }

    @Test
    void whenCheckoutCartItemListWithNonExistingProduct_ThenThrowException(){
        final List<Product> productList = Arrays.asList(
                new Product(1000L, "image1000", "name1000", 50, 4, "Description1000", 5, Type.WEIGHABLE, 1,
                            ProductCategory.FRUITS, 10),
                new Product(2L, "image2", "name2", 40, 3, "Description2", 6, Type.WEIGHABLE, 1,
                            ProductCategory.FRUITS, 10)
        );

        final List<CartItemDto> cartItemDtoList = Arrays.asList(
                new CartItemDto(ProductMapper.MAPPER.toDTOShort(productList.get(0)), 5),
                new CartItemDto(ProductMapper.MAPPER.toDTOShort(productList.get(1)), 8)
        );

        final Long productId = cartItemDtoList.get(0).getProductDtoShort().getId();

        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> cartService.checkoutProducts(cartItemDtoList));
    }

    @Test
    void whenCheckoutCartItemListWithNotSufficientQuantity_ThenThrowException(){
        final List<Product> productList = Arrays.asList(
                new Product(1L, "image1", "name1", 50, 4, "Description1", 5, Type.WEIGHABLE, 1,
                            ProductCategory.FRUITS, 10),
                new Product(2L, "image2", "name2", 40, 3, "Description2", 6, Type.WEIGHABLE, 1,
                            ProductCategory.FRUITS, 10)
        );

        final List<CartItemDto> cartItemDtoList = Arrays.asList(
                new CartItemDto(ProductMapper.MAPPER.toDTOShort(productList.get(0)), 500),
                new CartItemDto(ProductMapper.MAPPER.toDTOShort(productList.get(1)), 10)
        );

        final Long productId = cartItemDtoList.get(0).getProductDtoShort().getId();

        when(productRepository.findById(productId)).thenReturn(
                Optional.ofNullable(productList.get(0)));

        assertThatExceptionOfType(NotEnoughProductsInStockException.class)
                .isThrownBy(() -> cartService.checkoutProducts(cartItemDtoList));
    }

    @Test
    void whenCheckoutCartItemListForLoggedInUser_ThenReturnCorrespondingDto(){
        final User user = new User("johndoe@yahoo.com", "12345", Role.USER);
        final Authentication authMock = mock(Authentication.class);
        final SecurityContext securityContext = mock(SecurityContext.class);

        final List<Product> productList = Arrays.asList(
                new Product(1L, "image1", "name1", 50, 4, "Description1", 5, Type.WEIGHABLE, 1,
                            ProductCategory.FRUITS, 10),
                new Product(2L, "image2", "name2", 40, 3, "Description2", 6, Type.WEIGHABLE, 1,
                            ProductCategory.FRUITS, 10)
        );
        final Map<Product, Integer> cartItems = new HashMap<>();
        cartItems.put(productList.get(0), 5);
        cartItems.put(productList.get(1), 8);
        final Cart cart = new Cart(1L, new User(), cartItems);

        final List<CartItemDto> expectedDtoList = Arrays.asList(
                new CartItemDto(ProductMapper.MAPPER.toDTOShort(productList.get(0)), 5),
                new CartItemDto(ProductMapper.MAPPER.toDTOShort(productList.get(1)), 8)
        );

        final Long productId1 = productList.get(0).getId();
        final Long productId2 = productList.get(1).getId();

        when(authMock.getPrincipal()).thenReturn(new UserDetailsImpl(user));

        when(securityContext.getAuthentication()).thenReturn(authMock);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        when(cartRepository.findByUserId(user.getId())).thenReturn(cart);

        when(productRepository.findById(productId1)).thenReturn(
                Optional.ofNullable(productList.get(0)));

        when(productRepository.findById(productId2)).thenReturn(
                Optional.ofNullable(productList.get(1)));

        CartDto actualDto = cartService.checkoutProductsForLoggedInUser();

        assertThat(actualDto.getCartItemList())
                .overridingErrorMessage("Actual CartItem list is not equal to expected one")
                .containsExactlyInAnyOrderElementsOf(expectedDtoList);

        assertThat(actualDto.getTotalCost())
                .overridingErrorMessage("Actual total cost is not equal to expected one")
                .isEqualTo(570);

        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldGetCartItemListFromCart(){
        final User user = new User("adamsmith@yahoo.com", "12345", Role.USER);
        final Authentication authMock = mock(Authentication.class);
        final SecurityContext securityContext = mock(SecurityContext.class);

        final List<Product> productList = Arrays.asList(
                new Product(1L, "image1", "name1", 50, 4, "Description1", 5, Type.WEIGHABLE, 1,
                        ProductCategory.FRUITS, 1000),
                new Product(2L, "image2", "name2", 40, 3, "Description2", 6, Type.WEIGHABLE, 1,
                        ProductCategory.FRUITS, 1000)
        );
        final Map<Product, Integer> cartItems = new HashMap<>();
        cartItems.put(productList.get(0), 5);
        cartItems.put(productList.get(1), 8);
        final Cart cart = new Cart(1L, new User(), cartItems);

        final List<CartItemDto> expectedDtoList = Arrays.asList(
                new CartItemDto(ProductMapper.MAPPER.toDTOShort(productList.get(0)), 5),
                new CartItemDto(ProductMapper.MAPPER.toDTOShort(productList.get(1)), 8)
        );

        final Long productId1 = productList.get(0).getId();
        final Long productId2 = productList.get(1).getId();
       // =====================================================================

        when(securityContext.getAuthentication()).thenReturn(authMock);
        SecurityContextHolder.setContext(securityContext);

        when(authMock.getPrincipal()).thenReturn(new UserDetailsImpl(user));

        when(jwtTokenUtil.generateToken(user)).thenReturn("Adam's Token");
        when(jwtTokenUtil.getUsernameFromToken("Adam's Token"))
                .thenReturn("adamsmith@yahoo.com");


        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));


//        doReturn(1L).when(
//                userRepository.findByEmail(user.getEmail())
//                .orElseThrow(UserNotFoundException::new))
//                .getId();

        when(userRepository.findByEmail(user.getEmail())
                .orElseThrow(UserNotFoundException::new)
                .getId())
                .thenReturn(1L);


        when(authMock.getPrincipal()).thenReturn(new UserDetailsImpl(user));



        CartDto actualDto = cartService.getCartItems("Adam's Token");

        assertThat(5).isEqualTo(5);

        SecurityContextHolder.clearContext();

    }

    @Test
    void shouldAddItemsToCard(){

    }
}
