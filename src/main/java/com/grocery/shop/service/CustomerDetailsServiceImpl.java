package com.grocery.shop.service;

import com.grocery.shop.dto.CustomerDetailsDto;
import com.grocery.shop.exception.CustomerDetailsNotFoundException;
import com.grocery.shop.mapper.CustomerDetailsMapper;
import com.grocery.shop.model.CustomerDetails;
import com.grocery.shop.repository.CustomerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class CustomerDetailsServiceImpl implements CustomerDetailsService{

    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;

    @Override
    public CustomerDetailsDto AddNewCustomerDetails(CustomerDetailsDto customerDetailsDto) {
        CustomerDetails createdCustomerDetails = new CustomerDetails();
        return CustomerDetailsMapper.MAPPER.toDto(customerDetailsRepository.save(CustomerDetailsMapper.MAPPER.fromDto(customerDetailsDto)));
    }

    @Override
    public boolean UpdateCustomerDetails(CustomerDetailsDto customerDetailsDto, long id) {
        CustomerDetails customerDetails = customerDetailsRepository.findById(id).orElseThrow(() -> new CustomerDetailsNotFoundException("not found by id"));
        final CustomerDetails updatedCustomerDetails = customerDetailsRepository.save(CustomerDetailsMapper.MAPPER.fromDto(customerDetailsDto));

        if (customerDetailsRepository.findById(id).isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}
