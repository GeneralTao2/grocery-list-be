package com.grocery.shop.controller;

import com.grocery.shop.dto.CustomerDetailsDto;
import com.grocery.shop.service.CustomerDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CustomerDetailsController {
    private final CustomerDetailsService customerDetailsService;

    @PostMapping("/customerDetails")
    public ResponseEntity<CustomerDetailsDto> createEmployee(@RequestBody CustomerDetailsDto customerDetailsDto){
        return new ResponseEntity<>(customerDetailsService.addNewCustomerDetails(customerDetailsDto), HttpStatus.OK) ;
    }

    @PutMapping("/customerDetails/{id}")
    public ResponseEntity<String> updateCustomerDetailsById(@PathVariable(value = "id") int customerDetails_id, @RequestBody CustomerDetailsDto customerDetailsDto){
        if(customerDetailsService.updateCustomerDetails(customerDetailsDto,customerDetails_id)){
            return new ResponseEntity<>("CustomerDetails is replaced successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("CustomerDetails hasn't been replaced due to some problem", HttpStatus.BAD_REQUEST);
        }
    }

}
