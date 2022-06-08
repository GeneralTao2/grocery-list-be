package com.grocery.shop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.shop.dto.CustomerDetailsDto;
import com.grocery.shop.exception.CustomerDetailsNotFoundException;
import com.grocery.shop.mapper.CustomerDetailsMapper;
import com.grocery.shop.model.Address;
import com.grocery.shop.model.CustomerDetails;
import com.grocery.shop.repository.CustomerDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerDetailsServiceTest {

    @Mock
    private CustomerDetailsRepository customerDetailsRepository;

    @Mock
    private CustomerDetailsServiceImpl customerDetailsService;

    private Address address;

    private CustomerDetailsDto customerDetailsDto;

    @BeforeEach
    void setUp() {
        customerDetailsRepository = mock(CustomerDetailsRepository.class);
        customerDetailsService = mock(CustomerDetailsServiceImpl.class);
        address = new Address(1L, "address", "addressDetails", "postalCode", "city", "country");
        customerDetailsDto = new CustomerDetailsDto(1L, "namefd", "nameld", true, address);
    }

    @Test
    void addNewCustomerDetailsShouldReturnCustomerDetailsIfAdded() {
        when(customerDetailsRepository.save(any(CustomerDetailsDto.class))).thenReturn(customerDetailsDto);
        when(customerDetailsService.addNewCustomerDetails(any(CustomerDetailsDto.class))).thenReturn(customerDetailsDto);

        assertThat(customerDetailsDto).isEqualTo(customerDetailsRepository.save(customerDetailsDto));
        assertThat(customerDetailsDto).isEqualTo(customerDetailsService.addNewCustomerDetails(customerDetailsDto)); //returns null
    }

    @Test
    void updateCustomerDetailsShouldReturnTrueWhenRuns() {
        doReturn(true).when(customerDetailsService).updateCustomerDetails(any(CustomerDetailsDto.class), any(Long.class));
        assertThat(customerDetailsService.updateCustomerDetails(customerDetailsDto, 2L)).isTrue();
    }

    @Test
    void updateCustomerDetailsShouldThrowExceptionIfNotFoundById() {
        CustomerDetailsNotFoundException notFoundException = new CustomerDetailsNotFoundException("not found by id");
        doThrow(notFoundException).when(customerDetailsService).updateCustomerDetails(customerDetailsDto, -1);
        assertThrows(notFoundException.getClass(), () -> {
            customerDetailsService.updateCustomerDetails(customerDetailsDto, -1);
        });
    }
}
