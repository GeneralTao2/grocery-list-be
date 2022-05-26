package com.grocery.shop.service;

import com.grocery.shop.dto.ProductDtoFull;
import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.exception.PageNotFoundException;
import com.grocery.shop.exception.ProductsNotFoundException;
import com.grocery.shop.mapper.ProductMapper;
import com.grocery.shop.model.Product;
import com.grocery.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

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

    @Override
    public List<ProductDtoShort> getPageWithProductsOnDashboard(final int pageNumber) {
        return getPage(productRepository.findAll(), 40, pageNumber);
    }

    @Override
    public List<ProductDtoShort> getMostPopularProducts() {
        List<ProductDtoShort> mostPopularProducts = productRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Product::getCountOfSoldProducts).reversed())
                .limit(15)
                .map(ProductMapper.MAPPER::toDTOShort)
                .collect(Collectors.toList());
        if (mostPopularProducts.isEmpty()) {
            throw new ProductsNotFoundException("Not enough products");
        }
        return mostPopularProducts;
    }

    @Override
    public ProductDtoFull getProductDescriptionById(long id) {
        return productRepository.findById(id).map(ProductMapper.MAPPER::toDtoFull).orElseThrow();
    }

    @Override
    public long getTotalPageNumber() {
        return productRepository.count() != 0 ? productRepository.count() / 12 + 1 : 0;
    }

    @Override
    public Page<ProductDtoShort> getPageWithProductsWithName(String name, int pageNumber) {
        final int pageSize = 15;

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);

        if(isNull(name)){
            return getProductPage(productRepository.findAll(pageable), pageable);
        }

        return getProductPage(productRepository.searchByName(name, pageable), pageable);
    }

    private Page<ProductDtoShort> getProductPage(Page<Product> productPage, Pageable pageable) {
        List<ProductDtoShort> productDtoList = productPage.stream()
                                                          .map(ProductMapper.MAPPER::toDTOShort)
                                                          .collect(Collectors.toList());

        Page<ProductDtoShort> resultPage = new PageImpl<>(productDtoList, pageable, productPage.getTotalElements());

        if((pageable.getPageNumber() + 1 > resultPage.getTotalPages()) && pageable.getPageNumber() != 0){
            throw new PageNotFoundException("This page does not exist");
        }
        if(productDtoList.isEmpty()){
            throw new ProductsNotFoundException("Products not found for this name");
        }

        return resultPage;
    @Override
    public List<ProductDtoShort> getMostPopularProducts() {
        List<ProductDtoShort> mostPopularProducts = productRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Product::getCountOfSoldProducts).reversed())
                .limit(15)
                .map(ProductMapper.MAPPER::toDTOShort)
                .collect(Collectors.toList());
        if (mostPopularProducts.isEmpty()) {
            throw new ProductsNotFoundException("Not enough products");
        }
        return mostPopularProducts;
    }

    @Override
    public ProductDtoFull getProductDescriptionById(long id) {
        return productRepository.findById(id).map(ProductMapper.MAPPER::toDtoFull).orElseThrow();
    }

    @Override
    public long getTotalPageNumber() {
        return productRepository.count() != 0 ? productRepository.count() / 12 + 1 : 0;
    }
}
