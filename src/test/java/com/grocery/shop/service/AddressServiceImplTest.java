package com.grocery.shop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.shop.mapper.CustomerDetailsMapper;
import com.grocery.shop.model.Address;
import com.grocery.shop.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeastOnce;

public class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressServiceImpl addressService;

    private Address address;

    @BeforeEach
    void setUp(){
        addressRepository = mock(AddressRepository.class);
        addressService = mock(AddressServiceImpl.class);
    }

    @Test
    void addressServiceInterractsWithAddressRepository() {
        when(addressService.getAddressById(Mockito.anyLong())).thenReturn(address);
        address = addressService.getAddressById(1L);
        Mockito.verify(addressService, atLeastOnce()).getAddressById(Mockito.anyLong());
    }
}
