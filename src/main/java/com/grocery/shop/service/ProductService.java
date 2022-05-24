package com.grocery.shop.service;

import com.grocery.shop.dto.ProductDtoFull;
import com.grocery.shop.dto.ProductDtoShort;

import java.util.List;

public interface ProductService {

    List<ProductDtoShort> getPageWithProductsOnDashboard(final int pageNumber);

    ProductDtoFull getProductDescriptionById(long id);

    public long getTotalPageNumber();
}
