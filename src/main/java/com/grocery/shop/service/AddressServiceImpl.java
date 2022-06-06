package com.grocery.shop.service;

import com.grocery.shop.model.Address;
import com.grocery.shop.repository.AddressRepository;

public class AddressServiceImpl implements AddressService{

    AddressRepository addressRepository;

    @Override
    public Address getAddressById(long id) {
        return addressRepository.getById(id);
    }
}
