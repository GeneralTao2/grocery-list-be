package com.grocery.shop.service;

import com.grocery.shop.dto.ProductDtoFull;
import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.dto.ProductResponse;
import com.grocery.shop.exception.PageNotFoundException;
import com.grocery.shop.exception.ProductNotFoundException;
import com.grocery.shop.exception.ProductsNotFoundException;
import com.grocery.shop.mapper.ProductMapper;
import com.grocery.shop.model.Product;
import com.grocery.shop.model.ProductCategory;
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

    private static final int PAGE_SIZE = 12;

    private final ProductRepository productRepository;

    ProductResponse getPage(final List<Product> productList, final int resultsPerPage, final int pageNumber) {
        int lastPossiblePage = (int) Math.ceil(((double) productList.size()) / resultsPerPage);
        if (pageNumber < 1 || pageNumber > lastPossiblePage) {
            throw new PageNotFoundException("This page does not exist");
        }
        int skipCount = (pageNumber - 1) * resultsPerPage;

        ProductResponse productResponse = new ProductResponse();

        productResponse.setProductDtoShort(productList.stream()
                .skip(skipCount)
                .limit(resultsPerPage)
                .map(ProductMapper.MAPPER::toDTOShort)
                .collect(Collectors.toList()));

        productResponse.setNumberOfPages(productList.size() != 0 ? productList.size() / 12 + 1 : 0);
        productResponse.setNumberOfElements(productList.size());

        return productResponse;
    }

    @Override
    public ProductResponse getPageWithProductsOnDashboard(final int pageNumber) {
        return getPage(productRepository.findAll(), PAGE_SIZE, pageNumber);
    }

    @Override
    public ProductResponse getProductsListByCategory(ProductCategory productCategory, final int pageNumber) {
        return getPage(productRepository.findAllByCategory(productCategory), PAGE_SIZE, pageNumber);
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
        return productRepository.findById(id).map(ProductMapper.MAPPER::toDtoFull).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public ProductResponse getPageWithProductsWithName(String name, int pageNumber) {
        final int pageSize = 15;

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        if (isBlankString(name)) {
            throw new ProductsNotFoundException("Products not found for this name");
        }

        Page<ProductDtoShort> resultPage = getProductPage(productRepository.searchByName(name, pageable), pageable);

        return new ProductResponse(resultPage.getContent(), resultPage.getTotalPages(), (int) resultPage.getTotalElements());
    }

    private static boolean isBlankString(String string){
        return isNull(string) || string.trim().isEmpty();
    }

    private Page<ProductDtoShort> getProductPage(Page<Product> productPage, Pageable pageable) {
        List<ProductDtoShort> productDtoList = productPage.stream()
                .map(ProductMapper.MAPPER::toDTOShort)
                .collect(Collectors.toList());

        Page<ProductDtoShort> resultPage = new PageImpl<>(productDtoList, pageable, productPage.getTotalElements());

        if ((pageable.getPageNumber() + 1 > resultPage.getTotalPages()) && pageable.getPageNumber() != 0) {
            throw new PageNotFoundException("This page does not exist");
        }
        if (productDtoList.isEmpty()) {
            throw new ProductsNotFoundException("Products not found for this name");
        }
        return resultPage;
    }
}
