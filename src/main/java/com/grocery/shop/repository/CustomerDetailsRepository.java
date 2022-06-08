package com.grocery.shop.repository;

import com.grocery.shop.dto.CustomerDetailsDto;
import com.grocery.shop.model.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {
    CustomerDetailsDto save(CustomerDetailsDto customerDetailsDto);
}
