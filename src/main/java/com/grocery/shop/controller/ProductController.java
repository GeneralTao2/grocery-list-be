package com.grocery.shop.controller;

import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.service.ProductService;
import com.grocery.shop.service.ProductServiceImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @GetMapping(value = "/products")
    public List<ProductDtoShort> getPageWithProducts() {
        return productService.getPageWithProductsOnDashboard(1);
    }

    @GetMapping(value = "/products/page={page}")
    public List<ProductDtoShort> getPageWithProducts(@PathVariable("page") final int page) {
        return productService.getPageWithProductsOnDashboard(page);
    }

}
