package com.grocery.shop.service;

import com.grocery.shop.dto.CustomerDetailsDto;
import com.grocery.shop.model.Address;

public interface CustomerDetailsService {

    CustomerDetailsDto AddNewCustomerDetails(CustomerDetailsDto customerDetailsDto);

    boolean UpdateCustomerDetails(CustomerDetailsDto customerDetailsDto, long customerDetails_id);
}
