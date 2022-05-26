package com.grocery.shop.service;

import com.grocery.shop.dto.ProductDtoFull;
import com.grocery.shop.dto.ProductDtoShort;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    List<ProductDtoShort> getPageWithProductsOnDashboard(final int pageNumber);

    ProductDtoFull getProductDescriptionById(long id);

    long getTotalPageNumber();

    List<ProductDtoShort> getMostPopularProducts();

    Page<ProductDtoShort> getPageWithProductsWithName(final String name, final int pageNumber);
}
