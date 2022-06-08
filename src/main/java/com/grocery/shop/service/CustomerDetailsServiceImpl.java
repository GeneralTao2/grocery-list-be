package com.grocery.shop.service;

import com.grocery.shop.dto.CustomerDetailsDto;
import com.grocery.shop.exception.CustomerDetailsNotFoundException;
import com.grocery.shop.mapper.CustomerDetailsMapper;
import com.grocery.shop.model.CustomerDetails;
import com.grocery.shop.repository.CustomerDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDetailsServiceImpl implements CustomerDetailsService{

    @Autowired
    private final CustomerDetailsRepository customerDetailsRepository;

    @Override
    public CustomerDetailsDto addNewCustomerDetails(CustomerDetailsDto customerDetailsDto) {
        return CustomerDetailsMapper.MAPPER.toDto(customerDetailsRepository.save(CustomerDetailsMapper.MAPPER.fromDto(customerDetailsDto)));
    }

    @Override
    public boolean updateCustomerDetails(CustomerDetailsDto customerDetailsDto, long id) {
        CustomerDetails customerDetails = customerDetailsRepository.findById(id).orElseThrow(() -> new CustomerDetailsNotFoundException("not found by id"));
        final CustomerDetails updatedCustomerDetails = customerDetailsRepository.save(CustomerDetailsMapper.MAPPER.fromDto(customerDetailsDto));

        return customerDetailsRepository.findById(id).isPresent();
    }
}
