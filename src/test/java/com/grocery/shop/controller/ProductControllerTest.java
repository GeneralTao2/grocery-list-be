package com.grocery.shop.controller;

import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.dto.ProductResponse;
import com.grocery.shop.model.ProductCategory;
import com.grocery.shop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private ProductController productController;

    @Mock
    private ProductServiceImpl productService;

    @BeforeEach
    void initMocks() {
        productController = new ProductController(productService);
    }

    @Test
    void getProducts() {
        ProductDtoShort product1 = mock(ProductDtoShort.class);
        ProductDtoShort product2 = mock(ProductDtoShort.class);
        ProductDtoShort product3 = mock(ProductDtoShort.class);

        List<ProductDtoShort> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDtoShort(productList);

        when(productService.getPageWithProductsOnDashboard(Mockito.any(Integer.class)))
                .thenReturn(productResponse);

        ProductResponse returnResponse = productController.getPageWithProducts();
        returnResponse.setNumberOfElements(productList.size());

        assertEquals(3, returnResponse.getNumberOfElements());
        assertFalse((returnResponse.getNumberOfElements() == 2));
        Mockito.verify(productService).getPageWithProductsOnDashboard(Mockito.any(Integer.class));
    }

    @Test
    void getProductsWithPage() {
        ProductDtoShort product1 = mock(ProductDtoShort.class);
        ProductDtoShort product2 = mock(ProductDtoShort.class);
        ProductDtoShort product3 = mock(ProductDtoShort.class);

        List<ProductDtoShort> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDtoShort(productList);

        when(productService.getPageWithProductsOnDashboard(Mockito.any(Integer.class)))
                .thenReturn(productResponse);

        ProductResponse resultResponse = productController.getPageWithProducts(2);
        resultResponse.setNumberOfElements(productList.size());

        assertEquals(3, resultResponse.getNumberOfElements());
    }

    @Test
    void getProductsByCategory() {
        ProductDtoShort product1 = mock(ProductDtoShort.class);
        ProductDtoShort product2 = mock(ProductDtoShort.class);
        ProductDtoShort product3 = mock(ProductDtoShort.class);

        List<ProductDtoShort> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDtoShort(productList);

        when(productService.getProductsListByCategory(Mockito.any(ProductCategory.class),Mockito.any(Integer.class)))
                .thenReturn(productResponse);

        ResponseEntity<ProductResponse> resultResponse = productController.getPageWithProductsByCategory(ProductCategory.FRUITS);
        resultResponse.getBody().setNumberOfElements(productList.size());

        assertEquals(3, Objects.requireNonNull(resultResponse.getBody()).getNumberOfElements());
        assertFalse((resultResponse.getBody().getNumberOfElements() == 2));
        Mockito.verify(productService).getProductsListByCategory(Mockito.any(ProductCategory.class),Mockito.any(Integer.class));
    }

    @Test
    void getProductsWithPageByCategory() {
        ProductDtoShort product1 = mock(ProductDtoShort.class);
        ProductDtoShort product2 = mock(ProductDtoShort.class);
        ProductDtoShort product3 = mock(ProductDtoShort.class);

        List<ProductDtoShort> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDtoShort(productList);

        when(productService.getProductsListByCategory(Mockito.any(ProductCategory.class),Mockito.any(Integer.class)))
                .thenReturn(productResponse);

        ResponseEntity<ProductResponse> resultListWithPage = productController.getPageWithProductsByCategoryWithPage(ProductCategory.FRUITS,2);
        resultListWithPage.getBody().setNumberOfElements(productList.size());

        assertEquals(3, Objects.requireNonNull(resultListWithPage.getBody()).getNumberOfElements());
    }

    @Test
    void getMostPopularProductsTest() {
        List<ProductDtoShort> productList = Arrays.asList(
                mock(ProductDtoShort.class),
                mock(ProductDtoShort.class),
                mock(ProductDtoShort.class),
                mock(ProductDtoShort.class)
        );

        when(productService.getMostPopularProducts()).thenReturn(productList);

        List<ProductDtoShort> resultList = productController.getMostPopularProducts();

        assertEquals(4, resultList.size());
    }

    @Test
    void getProductsFilteredByName() {
        final String productName = "apple";
        final List<ProductDtoShort> productList = Arrays.asList(
                    mock(ProductDtoShort.class),
                    mock(ProductDtoShort.class),
                    mock(ProductDtoShort.class),
                    mock(ProductDtoShort.class)
        );

        final Page<ProductDtoShort> productPage = new PageImpl<>(productList);

        when(productService.getPageWithProductsWithName(Mockito.any(String.class), Mockito.any(Integer.class)))
                .thenReturn(productPage);

        final List<ProductDtoShort> resultList = productController.getPageWithProductsFilteredByName(productName);

        assertThat(resultList).containsExactlyInAnyOrderElementsOf(productPage.getContent());
    }

    @Test
    void getProductsFilteredByNameWithPage() {
        final String productName = "apple";
        final int pageNumber = 1;
        final List<ProductDtoShort> productList = Arrays.asList(
                mock(ProductDtoShort.class),
                mock(ProductDtoShort.class),
                mock(ProductDtoShort.class),
                mock(ProductDtoShort.class)
        );

        final Page<ProductDtoShort> productPage = new PageImpl<>(productList);

        when(productService.getPageWithProductsWithName(Mockito.any(String.class), Mockito.any(Integer.class)))
                .thenReturn(productPage);

        final List<ProductDtoShort> resultList = productController.getPageWithProductsFilteredByName(productName,
                                                                                                pageNumber);

        assertThat(resultList).containsExactlyInAnyOrderElementsOf(productPage.getContent());
    }
}
