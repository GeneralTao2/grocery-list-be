package com.grocery.shop.service;

import com.grocery.shop.dto.CustomerDetailsDto;

public interface CustomerDetailsService {

    CustomerDetailsDto addNewCustomerDetails(CustomerDetailsDto customerDetailsDto);

    boolean updateCustomerDetails(CustomerDetailsDto customerDetailsDto, long customerDetails_id);
}
