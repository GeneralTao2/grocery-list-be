package com.grocery.shop.service;

import com.grocery.shop.model.Address;
import com.grocery.shop.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;

    @Override
    public Address getAddressById(long id) {
        return addressRepository.getById(id);
    }
}
