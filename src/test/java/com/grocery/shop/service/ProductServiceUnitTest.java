package com.grocery.shop.service;

import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.exception.PageNotFoundException;
import com.grocery.shop.exception.ProductsNotFoundException;
import com.grocery.shop.model.Product;
import com.grocery.shop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
                new Product(1L, "Source1", "Name1", 150., 1.5, "Description1", 1),
                new Product(2L, "Source2", "Name2", 250., 2.5, "Description2", 2),
                new Product(3L, "Source3", "Name3", 350., 3.5, "Description3", 3),
                new Product(4L, "Source4", "Name4", 450., 4.5, "Description4", 4)
        );

        List<ProductDtoShort> expectedList = Arrays.asList(
                new ProductDtoShort(1L, "Source1", "Name1", 150., 1.5),
                new ProductDtoShort(2L, "Source2", "Name2", 250., 2.5),
                new ProductDtoShort(3L, "Source3", "Name3", 350., 3.5)
        );

        when(productRepository.findAll()).thenReturn(productList);
        List<ProductDtoShort> firstPage = productsService.getPage(productRepository.findAll(), 3, 1);

        assertEquals(expectedList, firstPage);
    }

    @Test
    void secondPageWillContainOneElementWhenOnlyFourProductsAndMaximumThreePerPage() {
        List<Product> productList = Arrays.asList(
                new Product(1L, "Source1", "Name1", 150., 1.5, "Description1", 1),
                new Product(2L, "Source2", "Name2", 250., 2.5, "Description2", 2),
                new Product(3L, "Source3", "Name3", 350., 3.5, "Description3", 3),
                new Product(4L, "Source4", "Name4", 450., 4.5, "Description4", 4)
        );

        List<ProductDtoShort> expectedList = Collections.singletonList(
                new ProductDtoShort(4L, "Source4", "Name4", 450., 4.5)
        );

        when(productRepository.findAll()).thenReturn(productList);
        List<ProductDtoShort> secondPage = productsService.getPage(productRepository.findAll(), 3, 2);

        assertEquals(expectedList, secondPage);
    }

    @Test()
    void ThrowExceptionWhenTooHighOrTooLowValueWasRequested() {
        List<Product> productList = Arrays.asList(
                new Product(1L, "Source1", "Name1", 150., 1.5, "Description1", 1),
                new Product(2L, "Source2", "Name2", 250., 2.5, "Description2", 2),
                new Product(3L, "Source3", "Name3", 350., 3.5, "Description3", 3),
                new Product(4L, "Source4", "Name4", 450., 4.5, "Description4", 4)
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
                    "Source", "Name", 150., 1.5, "Description", i));
        }

        for (int i = 0; i < 40; i++) {
            expectedList.add(new ProductDtoShort(1L + i,
                    "Source", "Name", 150., 1.5));
        }

        when(productRepository.findAll()).thenReturn(productList);
        List<ProductDtoShort> firstPage = productsService.getPageWithProductsOnDashboard(1);

        assertThat(firstPage).isEqualTo(expectedList);
    }

    @Test
    void getMostPopularProductsReturnsNoMoreThan15Products() {
        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            productList.add(new Product(1L + i,
                    "Source", "Name", 150., 1.5, "Description", i));
        }

        when(productRepository.findAll()).thenReturn(productList);
        assertEquals(15, productsService.getMostPopularProducts().size());
    }

    @Test
    void getMostPopularProductsReturnsProductsWithMostSales() {
        List<Product> productList = new ArrayList<>();
        List<ProductDtoShort> expectedList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            productList.add(new Product(1L + i,
                    "Source", "Name", 150., 1.5, "Description", i));
        }

        for (int i = 19; i > 4; i--) {
            expectedList.add(new ProductDtoShort(1L + i,
                    "Source", "Name", 150., 1.5));
        }

        when(productRepository.findAll()).thenReturn(productList);
        assertEquals(expectedList, productsService.getMostPopularProducts());
    }

    @Test
    void getMostPopularProductsThrowsExceptionWhenNotEnoughMatchingProducts() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ProductsNotFoundException.class, () -> productsService.getMostPopularProducts());
    }
}
