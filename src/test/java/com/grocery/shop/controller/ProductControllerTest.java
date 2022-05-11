package com.grocery.shop.controller;

import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.service.ProductService;
import com.grocery.shop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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

        when(productService.getPageWithProductsOnDashboard(Mockito.any(Integer.class)))
                .thenReturn(productList);

        List<ProductDtoShort> resultList = productController.getPageWithProducts();

        assertEquals(3, resultList.size());
        assertFalse((resultList.size() == 2));
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

        when(productService.getPageWithProductsOnDashboard(Mockito.any(Integer.class)))
                .thenReturn(productList);

        List<ProductDtoShort> resultListWithPage = productController.getPageWithProducts(2);

        assertEquals(3, resultListWithPage.size());
    }
}
