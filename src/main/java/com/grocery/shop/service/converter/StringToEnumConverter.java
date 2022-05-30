package com.grocery.shop.service.converter;

import com.grocery.shop.exception.ProductCategoryNotFound;
import com.grocery.shop.model.ProductCategory;
import org.springframework.core.convert.converter.Converter;


public class StringToEnumConverter implements Converter<String, ProductCategory> {
    @Override
    public ProductCategory convert(String source) {
        try {
            return ProductCategory.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ProductCategoryNotFound("This category does not exists");
        }
    }
}
