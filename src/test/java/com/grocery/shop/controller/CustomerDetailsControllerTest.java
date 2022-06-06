package com.grocery.shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.shop.dto.CustomerDetailsDto;
import com.grocery.shop.model.Address;
import com.grocery.shop.service.CustomerDetailsService;
import com.grocery.shop.service.CustomerDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.setRemoveAssertJRelatedElementsFromStackTrace;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CustomerDetailsController.class)
public class CustomerDetailsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerDetailsServiceImpl customerDetailsService;

    @MockBean
    private Address address;

    ObjectMapper mapper;

    @Test
    void postCustomerDetailsReturnsResponseEntityIfObjectSaved_couldThrowException() throws Exception {

        CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto(1L, "firstName", "lastName", true, address);
        int okStatus = HttpStatus.OK.value();
        final String path = "/customerDetails";
        String customerDetailsJson = mapper.writeValueAsString(customerDetailsDto);

        when(customerDetailsService.AddNewCustomerDetails(customerDetailsDto)).thenReturn(customerDetailsDto);

        MockHttpServletResponse response = mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDetailsJson))
                .andReturn()
                .getResponse();

        String body = response.getContentAsString();
        CustomerDetailsDto responseDto = mapper.readValue(body, CustomerDetailsDto.class);

        assertThat(responseDto).isEqualTo(customerDetailsDto);
        assertThat(response.getStatus()).isEqualTo(okStatus);
    }

    @Test
    void putCustomerDetailsShouldReturnStringResponse(){

        CustomerDetailsDto customerDetailsDto = Mockito.mock(CustomerDetailsDto.class);

        when(customerDetailsService.UpdateCustomerDetails(customerDetailsDto, 1L)).thenReturn(true);

        String succeedMessage = "CustomerDetails is replaced successfully";
        String failMessage = "CustomerDetails hasn't been replaced due to some problem";

        ResponseEntity response = new ResponseEntity<>(customerDetailsService.UpdateCustomerDetails(customerDetailsDto, 1L), HttpStatus.OK);

        assertThat(response.getBody()).isEqualTo(succeedMessage);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());

        when(customerDetailsService.UpdateCustomerDetails(customerDetailsDto, 1L)).thenReturn(false);
        response = new ResponseEntity<>(customerDetailsService.UpdateCustomerDetails(customerDetailsDto, 1L), HttpStatus.BAD_REQUEST);

        assertThat(response.getBody()).isEqualTo(failMessage);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
