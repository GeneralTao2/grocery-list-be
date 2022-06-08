package com.grocery.shop.controller;

import com.grocery.shop.dto.CustomerDetailsDto;
import com.grocery.shop.model.Address;
import com.grocery.shop.service.CustomerDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(CustomerDetailsController.class)
public class CustomerDetailsControllerTest extends SecurityContextMocking {

    @MockBean
    private CustomerDetailsController customerDetailsController;

    @Mock
    private CustomerDetailsServiceImpl customerDetailsService;

    @Mock
    private Address address;

    @BeforeEach
    void setUp() {
        customerDetailsService = mock(CustomerDetailsServiceImpl.class);
        address = mock(Address.class);
        customerDetailsController = mock(CustomerDetailsController.class);
    }

    @Test
    void postCustomerDetailsReturnsObjectSaved_couldThrowException() throws Exception {

        CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto(1L, "firstName", "lastName", true, address);

        when(customerDetailsService.addNewCustomerDetails(customerDetailsDto)).thenReturn(customerDetailsDto);

        CustomerDetailsDto responseDto = customerDetailsService.addNewCustomerDetails(customerDetailsDto);

        assertThat(responseDto).isEqualTo(customerDetailsDto);
    }

    @Test
    void putCustomerDetailsShouldReturnStringResponse(){

        CustomerDetailsDto customerDetailsDto = mock(CustomerDetailsDto.class);

        when(customerDetailsService.updateCustomerDetails(customerDetailsDto, 1L)).thenReturn(true);

        ResponseEntity response = new ResponseEntity<>(customerDetailsService.updateCustomerDetails(customerDetailsDto, 1L), HttpStatus.OK);

        assertThat(response.getBody()).isEqualTo(true);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        when(customerDetailsService.updateCustomerDetails(customerDetailsDto, 1L)).thenReturn(false);
        response = new ResponseEntity<>(customerDetailsService.updateCustomerDetails(customerDetailsDto, 1L), HttpStatus.BAD_REQUEST);

        assertThat(response.getBody()).isEqualTo(false);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
