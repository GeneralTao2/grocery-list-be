package com.grocery.shop.controller;

import com.grocery.shop.dto.ProductDtoFull;
import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.dto.ProductResponse;
import com.grocery.shop.model.ProductCategory;
import com.grocery.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping(value = "/products")
    public ProductResponse getPageWithProducts() {
        return productService.getPageWithProductsOnDashboard(1);
    }

    @GetMapping(value = "/products/page={page}")
    public ProductResponse getPageWithProducts(@PathVariable("page") final int page) {
        return productService.getPageWithProductsOnDashboard(page);
    }

    @GetMapping(value = "/product/{id}")
    public ProductDtoFull getProductDescriptionById(@PathVariable("id") long id) {
        return productService.getProductDescriptionById(id);
    }

    @GetMapping(value = "/products/category={category}")
    public ResponseEntity<ProductResponse> getPageWithProductsByCategory(@PathVariable("category") final ProductCategory category) {
        return new ResponseEntity<>(productService.getProductsListByCategory(category, 1), HttpStatus.OK);
    }

    @GetMapping(value = "/popular-products")
    public List<ProductDtoShort> getMostPopularProducts() {
        return productService.getMostPopularProducts();
    }

    @GetMapping(value = "/products/name={name}")
    public ProductResponse getPageWithProductsFilteredByName(@PathVariable("name") final String name) {
        return productService.getPageWithProductsWithName(name, 1);
    }

    @GetMapping(value = "/products/name={name}/page={page}")
    public ProductResponse getPageWithProductsFilteredByName(@PathVariable("name") final String name,
                                                                   @PathVariable("page") final int page){
        return productService.getPageWithProductsWithName(name, page);
    }

    @GetMapping(value = "/products/category={category}/page={page}")
    public ResponseEntity<ProductResponse> getPageWithProductsByCategoryWithPage(
            @PathVariable("category") final ProductCategory category, @PathVariable("page") final int page) {
        return new ResponseEntity<>(productService.getProductsListByCategory(category, page), HttpStatus.OK);
    }
}
