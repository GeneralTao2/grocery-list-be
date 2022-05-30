package com.grocery.shop.service;

import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.exception.PageNotFoundException;
import com.grocery.shop.exception.ProductsNotFoundException;
import com.grocery.shop.mapper.ProductMapper;
import com.grocery.shop.model.Product;
import com.grocery.shop.model.Type;
import com.grocery.shop.model.ProductCategory;
import com.grocery.shop.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
                new Product(1L, "Source1", "Name1", 150., 1.5, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(2L, "Source2", "Name2", 250., 2.5, "desc2", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(3L, "Source3", "Name3", 350., 3.5, "desc3", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(4L, "Source4", "Name4", 450., 4.5, "desc4", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS)
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
                new Product(1L, "Source1", "Name1", 150., 1.5, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(2L, "Source2", "Name2", 250., 2.5, "desc2", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(3L, "Source3", "Name3", 350., 3.5, "desc3", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(4L, "Source4", "Name4", 450., 4.5, "desc4", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS)
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
                new Product(1L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(2L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(3L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(4L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS)
        );

        when(productRepository.findAll()).thenReturn(productList);
        assertThrows(PageNotFoundException.class, () -> productsService.getPage(productRepository.findAll(), 3, 10));
        assertThrows(PageNotFoundException.class, () -> productsService.getPage(productRepository.findAll(), 3, -1));
    }

    @Test
    void returnsNoMoreThan12ProductsPerPage() {
        List<Product> productList = new ArrayList<>();
        List<ProductDtoShort> expectedList = new ArrayList<>();
        final int expectedPageSize = 12;
        final int productQuantity = 50;


        for (int i = 0; i < productQuantity; i++) {
            productList.add(new Product(1L + i,
                    "Source", "Name", 150., 1.5, "Description", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS));
        }

        for (int i = 0; i < expectedPageSize; i++) {
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
                    "Source", "Name", 150., 1.5, "Description", 3, Type.WEIGHABLE, i, ProductCategory.FRUITS));
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
                    "Source", "Name", 150., 1.5, "Description", i, Type.WEIGHABLE, i, ProductCategory.FRUITS));
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

    @Test
    void getProductsByNameReturnsOnlyProductsWithRequestedName() {
        final String productName = "Apple";
        final List<Product> productList = Arrays.asList(
                new Product(1L, "Source1", "Apple", 150., 1, "Description1", 4, Type.WEIGHABLE, 1, ProductCategory.FRUITS),
                new Product(2L, "Source2", "Apple Juice", 350., 3.5, "Description2", 3, Type.WEIGHABLE, 4, ProductCategory.FRUITS)
        );
        final List<ProductDtoShort> productDtoList = productList.stream()
                .map(ProductMapper.MAPPER::toDTOShort)
                .collect(Collectors.toList());

        final Page<ProductDtoShort> expectedProductPage = new PageImpl<>(productDtoList);

        when(productRepository.searchByName(eq(productName), any(Pageable.class)))
                .thenReturn(new PageImpl<>(productList));

        final Page<ProductDtoShort> actualProductPage = productsService.getPageWithProductsWithName(productName, 1);

        Assertions.assertThat(actualProductPage.getContent()).containsExactlyInAnyOrderElementsOf(expectedProductPage.getContent());
    }

    @Test
    void getProductsByNameReturnsNoMoreThanFifteenProductsPerPage() {
        final List<Product> productList = new ArrayList<>();
        final List<ProductDtoShort> expectedList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            productList.add(new Product(1L + i,
                    "Source", "Name", 150., 1.5, "Description", 2, Type.WEIGHABLE, 5, ProductCategory.FRUITS));
        }

        for (int i = 0; i < 15; i++) {
            expectedList.add(new ProductDtoShort(1L + i,
                    "Source", "Name", 150., 1.5));
        }

        when(productRepository.searchByName(eq("Name"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(productList.subList(0, 15)));

        final List<ProductDtoShort> actualList = productsService.getPageWithProductsWithName("Name", 1)
                .getContent();

        Assertions.assertThat(actualList).containsExactlyInAnyOrderElementsOf(expectedList);
    }

    @Test
    void getProductsByNameThrowsExceptionForNonExistingPage() {
        final String productName = "Apple";
        final int pageNumber = 8;

        when(productRepository.searchByName(any(String.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        assertThatExceptionOfType(PageNotFoundException.class)
                .isThrownBy(() -> productsService.getPageWithProductsWithName(productName, pageNumber));
    }

    @Test
    void getProductsByNameThrowsExceptionForNonExistingProductName() {
        final String productName = "absent";

        final Page<Product> emptyPage = new PageImpl<>(List.of());

        when(productRepository.searchByName(eq(productName), any(Pageable.class)))
                .thenReturn(emptyPage);

        assertThatExceptionOfType(ProductsNotFoundException.class)
                .isThrownBy(() -> productsService.getPageWithProductsWithName(productName, 1));
    }

    @Test
    void getProductsByNameReturnsDefaultProductPageForNullName() {
        final List<Product> productList = Arrays.asList(
                new Product(1L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(2L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(3L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(4L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS)
        );
        final List<ProductDtoShort> productDtoList = productList.stream()
                .map(ProductMapper.MAPPER::toDTOShort)
                .collect(Collectors.toList());

        final Page<ProductDtoShort> expectedProductPage = new PageImpl<>(productDtoList);

        when(productRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(productList));

        final Page<ProductDtoShort> actualProductPage = productsService.getPageWithProductsWithName(null, 1);

        Assertions.assertThat(actualProductPage.getContent()).containsExactlyInAnyOrderElementsOf(expectedProductPage.getContent());
    }

    @Test
    void firstPageCannotContainMoreProductsThenRequestedByProductCategory() {
        List<Product> productList = Arrays.asList(
                new Product(1L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(2L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(3L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(4L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS)
        );
        List<ProductDtoShort> expectedList = Arrays.asList(
                new ProductDtoShort(1L, "source", "name", 150.3, 3.3),
                new ProductDtoShort(2L, "source", "name", 150.3, 3.3),
                new ProductDtoShort(3L, "source", "name", 150.3, 3.3)
        );
        when(productRepository.findAllByCategory(ProductCategory.FRUITS)).thenReturn(productList);
        List<ProductDtoShort> firstPage = productsService.getPage(productRepository.findAllByCategory(ProductCategory.FRUITS), 3, 1);
        assertEquals(expectedList, firstPage);
    }

    @Test
    void secondPageWillContainOneElementSelectedByCategoryWhenOnlyFourProductsAndMaximumThreePerPage() {
        List<Product> productList = Arrays.asList(
                new Product(1L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(2L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(3L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS),
                new Product(4L, "source", "name", 150.3, 3.3, "desc1", 3, Type.WEIGHABLE, 3, ProductCategory.FRUITS)
        );

        List<ProductDtoShort> expectedList = Collections.singletonList(
                new ProductDtoShort(4L, "source", "name", 150.3, 3.3)
        );

        when(productRepository.findAll()).thenReturn(productList);
        List<ProductDtoShort> secondPage = productsService.getPage(productRepository.findAll(), 3, 2);

        assertEquals(expectedList, secondPage);

    }

    @Test
    void returnsNoMoreThanFortyProductsSelectedByCategoryPerPage() {
        List<Product> productList = new ArrayList<>();
        List<ProductDtoShort> expectedList = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            productList.add(new Product(1L + i,
            "Source", "Name", 150., 1.5, "Description", 2, Type.WEIGHABLE, 5, ProductCategory.FRUITS));
        }

        for (int i = 0; i < 12; i++) {
            expectedList.add(new ProductDtoShort(1L + i,
                    "Source", "Name", 150., 1.5));
        }

        when(productRepository.findAllByCategory(ProductCategory.FRUITS)).thenReturn(productList);
        assertEquals(12, productsService.getProductsListByCategory(ProductCategory.FRUITS,1).size());
        assertEquals(expectedList, productsService.getProductsListByCategory(ProductCategory.FRUITS,1));
    }

}
