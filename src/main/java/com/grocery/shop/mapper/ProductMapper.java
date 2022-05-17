package com.grocery.shop.mapper;

import com.grocery.shop.dto.ProductDtoFull;
import com.grocery.shop.dto.ProductDtoShort;
import com.grocery.shop.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    ProductDtoFull toDtoFull(Product product);

    ProductDtoShort toDTOShort(Product product);

    Product fromDtoFull(ProductDtoFull productDtoFull);

    Product fromDtoShort(ProductDtoShort productDtoShort);
}
