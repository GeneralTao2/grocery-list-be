package com.grocery.shop.mapper;

import com.grocery.shop.dto.CustomerDetailsDto;
import com.grocery.shop.model.CustomerDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerDetailsMapper {
    CustomerDetailsMapper MAPPER = Mappers.getMapper(CustomerDetailsMapper.class);

    CustomerDetailsDto toDto(CustomerDetails customerDetails);

    CustomerDetails fromDto(CustomerDetailsDto customerDetailsDto);
}
