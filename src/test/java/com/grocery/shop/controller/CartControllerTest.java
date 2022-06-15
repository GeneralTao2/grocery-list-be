package com.grocery.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.shop.dto.CartDto;
import com.grocery.shop.dto.CartItemDto;
import com.grocery.shop.dto.ErrorResponse;
import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.dto.ProductWithQuantity;
import com.grocery.shop.dto.QuantityResponse;
import com.grocery.shop.dto.UserDetailsImpl;
import com.grocery.shop.exception.NoMoreProductsInStockException;
import com.grocery.shop.exception.ProductNotFoundException;
import com.grocery.shop.model.Role;
import com.grocery.shop.model.Type;
import com.grocery.shop.model.User;
import com.grocery.shop.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest extends SecurityContextMocking{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;

    @Test
    void whenPostCartItemListWithSufficientQuantity_ThenReturnCartContent() throws Exception {
        final CartItemDto cartItemDto1 = new CartItemDto(new ProductDtoShort(1L, "image1", "name1", 50, 4,
                                                                             Type.WEIGHABLE, 1), 5);
        final CartItemDto cartItemDto2 = new CartItemDto(new ProductDtoShort(2L, "image2", "name2", 40, 3,
                                                                             Type.WEIGHABLE, 1), 10);
        final CartItemDto cartItemDto3 = new CartItemDto(new ProductDtoShort(3L, "image3", "name3", 20, 4,
                                                                             Type.WEIGHABLE, 1), 8);
        final List<CartItemDto> cartItemDtoList = List.of(cartItemDto1, cartItemDto2, cartItemDto3);

        final CartDto expectedDto = new CartDto(cartItemDtoList, 810);

        when(cartService.checkoutProducts(cartItemDtoList)).thenReturn(expectedDto);

        final MvcResult mvcResult = mockMvc.perform(post("/cart/checkout").contentType(APPLICATION_JSON)
                                                                                   .content(objectMapper.writeValueAsString(cartItemDtoList)))
                                           .andExpect(status().isOk())
                                           .andReturn();
        final String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody)
                .overridingErrorMessage("Returned response body is not equal to expected")
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedDto));

    }

    @Test
    void whenGetCartContentByUserId_ThenReturnCorrespondingCartDto() throws Exception {
        final UserDetails userDetails = new UserDetailsImpl(new User("johndoe@yahoo.com", "12345", Role.USER));

        final CartItemDto cartItemDto1 = new CartItemDto(new ProductDtoShort(1L, "image1", "name1", 50, 4,
                                                                             Type.WEIGHABLE, 1), 5);
        final CartItemDto cartItemDto2 = new CartItemDto(new ProductDtoShort(2L, "image2", "name2", 40, 3,
                                                                             Type.WEIGHABLE, 1), 10);
        final CartItemDto cartItemDto3 = new CartItemDto(new ProductDtoShort(3L, "image3", "name3", 20, 4,
                                                                             Type.WEIGHABLE, 1), 8);
        final List<CartItemDto> cartItemDtoList = List.of(cartItemDto1, cartItemDto2, cartItemDto3);

        final CartDto expectedDto = new CartDto(cartItemDtoList, 810);

        when(cartService.checkoutProductsForLoggedInUser()).thenReturn(expectedDto);

        final MvcResult mvcResult =
                mockMvc.perform(get("/user/cart/checkout").with(user(userDetails))
                                                          .contentType(APPLICATION_JSON))
                                           .andExpect(status().isOk())
                                           .andReturn();
        final String actualResponseBody = mvcResult.getResponse().getContentAsString();

        verify(cartService).checkoutProductsForLoggedInUser();

        assertThat(actualResponseBody)
                .overridingErrorMessage("Returned response body is not equal to expected")
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedDto));

    }

    @Test
    void addOne_AddExistingProductToEmptyCart_ReturnQuantityResponse() throws Exception {
        final UserDetails userDetails =
                new UserDetailsImpl(
                        new User("johndoe@yahoo.com", "12345", Role.USER));
        final ProductWithQuantity productWithQuantity =
                new ProductWithQuantity(1L,1);
        final String productWithQuantityJson =
                objectMapper.writeValueAsString(productWithQuantity);
        final QuantityResponse expectedQuantityResponse =
                new QuantityResponse(HttpStatus.OK.value(), 1);
        final String path = "/user/cart/one";

        when(cartService.addOneProductQuantityInCart(productWithQuantity)).thenReturn(expectedQuantityResponse);

        final MockHttpServletResponse response = mockMvc.perform(post(path)
                        .with(user(userDetails))
                        .contentType(APPLICATION_JSON)
                        .content(productWithQuantityJson))
                .andReturn()
                .getResponse();

        final String actualQuantityResponseJson = response.getContentAsString();
        final QuantityResponse actualQuantityResponse =
                objectMapper.readValue(actualQuantityResponseJson, QuantityResponse.class);

        assertThat(actualQuantityResponse.getStatus())
                .isEqualTo(expectedQuantityResponse.getStatus());
        assertThat(actualQuantityResponse.getQuantity())
                .isEqualTo(expectedQuantityResponse.getQuantity());
    }

    @Test
    void addOne_AddNotExistingProductToEmptyCart_ThrowException() throws Exception {
        final UserDetails userDetails =
                new UserDetailsImpl(
                        new User("johndoe@yahoo.com", "12345", Role.USER));
        final long productId = 1L;
        final ProductWithQuantity productWithQuantity =
                new ProductWithQuantity(productId,1);
        final String productWithQuantityJson =
                objectMapper.writeValueAsString(productWithQuantity);

        final String path = "/user/cart/one";

        when(cartService.addOneProductQuantityInCart(productWithQuantity)).thenThrow(
                new ProductNotFoundException(productId)
        );

        final MockHttpServletResponse response = mockMvc.perform(post(path)
                        .with(user(userDetails))
                        .contentType(APPLICATION_JSON)
                        .content(productWithQuantityJson))
                .andReturn()
                .getResponse();

        final String actualErrorResponseJson = response.getContentAsString();
        final ErrorResponse actualErrorResponse =
                objectMapper.readValue(actualErrorResponseJson, ErrorResponse.class);

        assertThat(actualErrorResponse.getErrorCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(actualErrorResponse.getMessage())
                .contains(String.valueOf(productId));
    }

    @Test
    void addOne_AddMoreProductsThenExistsInStock_ThrowException() throws Exception {
        final UserDetails userDetails =
                new UserDetailsImpl(
                        new User("johndoe@yahoo.com", "12345", Role.USER));
        final long productId = 1L;
        final int productQuantityInStock = 2;
        final ProductWithQuantity productWithQuantity =
                new ProductWithQuantity(productId,1);
        final String productWithQuantityJson =
                objectMapper.writeValueAsString(productWithQuantity);

        final String path = "/user/cart/one";

        when(cartService.addOneProductQuantityInCart(productWithQuantity)).thenThrow(
                new NoMoreProductsInStockException(productQuantityInStock)
        );

        final MockHttpServletResponse response = mockMvc.perform(post(path)
                        .with(user(userDetails))
                        .contentType(APPLICATION_JSON)
                        .content(productWithQuantityJson))
                .andReturn()
                .getResponse();

        final String actualQuantityResponseJson = response.getContentAsString();
        final QuantityResponse actualQuantityResponse =
                objectMapper.readValue(actualQuantityResponseJson, QuantityResponse.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actualQuantityResponse.getStatus()).isEqualTo(452);
        assertThat(actualQuantityResponse.getQuantity())
                .isEqualTo(productQuantityInStock);
    }

    @Test
    void editOne_PassExistingProductWithQuantity_ReturnResponseQuantity() throws Exception {
        final UserDetails userDetails =
                new UserDetailsImpl(
                        new User("johndoe@yahoo.com", "12345", Role.USER));
        final ProductWithQuantity productWithQuantity =
                new ProductWithQuantity(1L,1);
        final String productWithQuantityJson =
                objectMapper.writeValueAsString(productWithQuantity);
        final QuantityResponse expectedQuantityResponse =
                new QuantityResponse(HttpStatus.OK.value(), 1);
        final String path = "/user/cart/one";

        final MockHttpServletResponse response = mockMvc.perform(put(path)
                        .with(user(userDetails))
                        .contentType(APPLICATION_JSON)
                        .content(productWithQuantityJson))
                .andReturn()
                .getResponse();

        verify(cartService).editOneProductQuantityInCart(productWithQuantity);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void editOne_PassProductWithQuantityWithNotExistingProduct_ThrowException() throws Exception {
        final UserDetails userDetails =
                new UserDetailsImpl(
                        new User("johndoe@yahoo.com", "12345", Role.USER));
        final long productId = 1L;
        final ProductWithQuantity productWithQuantity =
                new ProductWithQuantity(productId,1);
        final String productWithQuantityJson =
                objectMapper.writeValueAsString(productWithQuantity);

        final String path = "/user/cart/one";

        doThrow(new ProductNotFoundException(productId))
                .when(cartService).editOneProductQuantityInCart(productWithQuantity);


        final MockHttpServletResponse response = mockMvc.perform(put(path)
                        .with(user(userDetails))
                        .contentType(APPLICATION_JSON)
                        .content(productWithQuantityJson))
                .andReturn()
                .getResponse();

        final String actualErrorResponseJson = response.getContentAsString();
        final ErrorResponse actualErrorResponse =
                objectMapper.readValue(actualErrorResponseJson, ErrorResponse.class);

        assertThat(actualErrorResponse.getErrorCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(actualErrorResponse.getMessage())
                .contains(String.valueOf(productId));

    }

    @Test
    void editOne_PassProductWithQuantityWithMoreProductsThenInStock_ThrowException() throws Exception {
        final UserDetails userDetails =
                new UserDetailsImpl(
                        new User("johndoe@yahoo.com", "12345", Role.USER));
        final long productId = 1L;
        final int productQuantityInStock = 1;
        final ProductWithQuantity productWithQuantity =
                new ProductWithQuantity(productId,2);
        final String productWithQuantityJson =
                objectMapper.writeValueAsString(productWithQuantity);

        final String path = "/user/cart/one";

        doThrow(new NoMoreProductsInStockException(productQuantityInStock))
                .when(cartService).editOneProductQuantityInCart(productWithQuantity);


        final MockHttpServletResponse response = mockMvc.perform(put(path)
                        .with(user(userDetails))
                        .contentType(APPLICATION_JSON)
                        .content(productWithQuantityJson))
                .andReturn()
                .getResponse();

        final String actualQuantityResponseJson = response.getContentAsString();
        final QuantityResponse actualQuantityResponse =
                objectMapper.readValue(actualQuantityResponseJson, QuantityResponse.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actualQuantityResponse.getStatus()).isEqualTo(452);
        assertThat(actualQuantityResponse.getQuantity())
                .isEqualTo(productQuantityInStock);

    }


}
