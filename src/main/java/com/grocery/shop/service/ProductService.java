package com.grocery.shop.service;

import com.grocery.shop.dto.ProductDtoFull;
import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.dto.ProductResponse;
import com.grocery.shop.model.ProductCategory;

import java.util.List;

public interface ProductService {

    ProductResponse getPageWithProductsOnDashboard(final int pageNumber);

    ProductDtoFull getProductDescriptionById(long id);

    ProductResponse getProductsListByCategory(ProductCategory productCategory, final int pageNumber);

    List<ProductDtoShort> getMostPopularProducts();

    ProductResponse getPageWithProductsWithName(final String name, final int pageNumber);
}
