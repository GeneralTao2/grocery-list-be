package com.grocery.shop.dto;

import com.grocery.shop.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailsDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Boolean keepMeInTouch;
    private Address address;

}
