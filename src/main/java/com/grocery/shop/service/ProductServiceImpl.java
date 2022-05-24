package com.grocery.shop.service;

import com.grocery.shop.dto.ProductDtoFull;
import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.exception.PageNotFoundException;
import com.grocery.shop.mapper.ProductMapper;
import com.grocery.shop.model.Product;
import com.grocery.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {

    private final ProductRepository productRepository;

    List<ProductDtoShort> getPage(final List<Product> productList, final int resultsPerPage, final int pageNumber) {
        int lastPossiblePage = (int) Math.ceil(((double) productList.size()) / resultsPerPage);
        if (pageNumber < 1 || pageNumber > lastPossiblePage) {
            throw new PageNotFoundException("This page does not exist");
        }
        int skipCount = (pageNumber - 1) * resultsPerPage;

        return productList.stream()
                .skip(skipCount)
                .limit(resultsPerPage)
                .map(ProductMapper.MAPPER::toDTOShort)
                .collect(Collectors.toList());
    }

    public List<ProductDtoShort> getPageWithProductsOnDashboard(final int pageNumber) {
        return getPage(productRepository.findAll(), 40, pageNumber);
    }

        public ProductDtoFull getProductDescriptionById(long id) {
        return ProductMapper.MAPPER.toDtoFull(productRepository.findById(id).get());
    }

    public long getTotalPageNumber() {
        return productRepository.count() / 40 + 1;
    }
}
