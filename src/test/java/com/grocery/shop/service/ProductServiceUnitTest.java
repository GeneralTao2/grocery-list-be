package com.grocery.shop.service;

import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.exception.PageNotFoundException;
import com.grocery.shop.model.Product;
import com.grocery.shop.model.Type;
import com.grocery.shop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceUnitTest {

    private ProductRepository productRepository;
    private ProductServiceImpl productsService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productsService = new ProductServiceImpl(productRepository);
    }

    @Test
    void firstPageCannotContainMoreProductsThenRequested() {
        List<Product> productList = Arrays.asList(
                new Product(1L, "Source1", "Name1", 150., 1.5, "Description1", Type.WEIGHABLE, 4),
                new Product(2L, "Source2", "Name2", 250., 2.5, "Description2", Type.WEIGHABLE, 5),
                new Product(3L, "Source3", "Name3", 350., 3.5, "Description3", Type.WEIGHABLE, 4),
                new Product(4L, "Source4", "Name4", 450., 4.5, "Description4", Type.WEIGHABLE, 3)
        );

        List<ProductDtoShort> expectedList = Arrays.asList(
                new ProductDtoShort(1L, "Source1", "Name1", 150., 1.5),
                new ProductDtoShort(2L, "Source2", "Name2", 250., 2.5),
                new ProductDtoShort(3L, "Source3", "Name3", 350., 3.5)
        );

        when(productRepository.findAll()).thenReturn(productList);
        assertEquals(3, productsService.getPage(productRepository.findAll(), 3, 1).size());
        assertFalse(productsService.getPage(productRepository.findAll(), 3, 1).size() > 3);
        assertEquals(expectedList, productsService.getPage(productRepository.findAll(), 3, 1));
    }

    @Test
    void secondPageWillContainOneElementWhenOnlyFourProductsAndMaximumThreePerPage() {
        List<Product> productList = Arrays.asList(
                new Product(1L, "Source1", "Name1", 150., 1.5, "Description1", Type.WEIGHABLE, 4),
                new Product(2L, "Source2", "Name2", 250., 2.5, "Description2", Type.WEIGHABLE, 4),
                new Product(3L, "Source3", "Name3", 350., 3.5, "Description3", Type.WEIGHABLE, 4),
                new Product(4L, "Source4", "Name4", 450., 4.5, "Description4", Type.WEIGHABLE, 4)
        );

        List<ProductDtoShort> expectedList = Collections.singletonList(
                new ProductDtoShort(4L, "Source4", "Name4", 450., 4.5)
        );

        when(productRepository.findAll()).thenReturn(productList);
        assertEquals(1, productsService.getPage(productRepository.findAll(), 3, 2).size());
        assertEquals(expectedList, productsService.getPage(productRepository.findAll(), 3, 2));
    }

    @Test()
    void ThrowExceptionWhenTooHighOrTooLowValueWasRequested() {
        List<Product> productList = Arrays.asList(
                new Product(1L, "Source1", "Name1", 150., 1.5, "Description1", Type.WEIGHABLE, 4),
                new Product(2L, "Source2", "Name2", 250., 2.5, "Description2", Type.WEIGHABLE, 4),
                new Product(3L, "Source3", "Name3", 350., 3.5, "Description3", Type.WEIGHABLE, 4),
                new Product(4L, "Source4", "Name4", 450., 4.5, "Description4", Type.WEIGHABLE, 4)
        );

        when(productRepository.findAll()).thenReturn(productList);
        assertThrows(PageNotFoundException.class, () -> productsService.getPage(productRepository.findAll(), 3, 10));
        assertThrows(PageNotFoundException.class, () -> productsService.getPage(productRepository.findAll(), 3, -1));
    }

    @Test
    void returnsNoMoreThanFortyProductsPerPage() {
        List<Product> productList = new ArrayList<>();
        List<ProductDtoShort> expectedList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            productList.add(new Product(1L + i,
                    "Source", "Name", 150., 1.5, "Description", Type.WEIGHABLE, 5));
        }

        for (int i = 0; i < 40; i++) {
            expectedList.add(new ProductDtoShort(1L + i,
                    "Source", "Name", 150., 1.5));
        }

        when(productRepository.findAll()).thenReturn(productList);
        assertEquals(40, productsService.getPageWithProductsOnDashboard(1).size());
        assertEquals(expectedList, productsService.getPageWithProductsOnDashboard(1));
    }
}
