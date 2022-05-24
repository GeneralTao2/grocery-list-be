package com.grocery.shop.controller;

import com.grocery.shop.dto.ProductDtoFull;
import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping(value = "/products")
    public List<ProductDtoShort> getPageWithProducts() {
        return productService.getPageWithProductsOnDashboard(1);
    }

    @GetMapping(value = "/products/page={page}")
    public List<ProductDtoShort> getPageWithProducts(@PathVariable("page") final int page) {
        return productService.getPageWithProductsOnDashboard(page);
    }

    @GetMapping(value = "/popular-products")
    public List<ProductDtoShort> getMostPopularProducts() {
        return productService.getMostPopularProducts();
    }

    @GetMapping(value = "/products/name={name}")
    public List<ProductDtoShort> getPageWithProductsFilteredByName(@PathVariable("name") final String name) {
        return productService.getPageWithProductsWithName(name, 1).getContent();
    }

    @GetMapping(value = "/products/name={name}/page={page}")
    public List<ProductDtoShort> getPageWithProductsFilteredByName(@PathVariable("name") final String name,
                                                                   @PathVariable("page") final int page){
        return productService.getPageWithProductsWithName(name, page).getContent();
    }
    @GetMapping(value = "/product/{id}")
    public ProductDtoFull getProductDescriptionById(@PathVariable("id") long id) {
        return productService.getProductDescriptionById(id);
    }
}
