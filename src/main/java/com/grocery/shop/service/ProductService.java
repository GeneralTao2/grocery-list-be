package com.grocery.shop.service;

import com.grocery.shop.dto.ProductDtoShort;

import java.util.List;

public interface ProductService {

    List<ProductDtoShort> getPageWithProductsOnDashboard(final int pageNumber);

    List<ProductDtoShort> getMostPopularProducts();
}
