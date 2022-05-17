package com.grocery.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.shop.dto.UserDto;
import com.grocery.shop.exception.UserAlreadyExistsException;
import com.grocery.shop.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRegistrationController.class)
class UserRegistrationControllerTest {

    private static final UserDto USER_DTO = new UserDto("johndoe@yahoo.com", "123456pass");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void whenPostValidUserBody_ThenReturnCreatedResponse() throws Exception {
        doNothing().when(userService).saveUser(USER_DTO);

        mockMvc.perform(post("/register").contentType(APPLICATION_JSON)
                                         .content(objectMapper.writeValueAsString(USER_DTO)))
               .andExpect(status().isCreated());
    }

    @Test
    void whenPostInvalidUserEmail_ThenReturnBadRequestResponse() throws Exception {
        final UserDto invalidUserDto = new UserDto();
        invalidUserDto.setEmail("abcdasf");

        mockMvc.perform(post("/register").contentType(APPLICATION_JSON)
                                         .content(objectMapper.writeValueAsString(invalidUserDto)))
               .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostInvalidUserPassword_ThenReturnBadRequestResponse() throws Exception {
        final UserDto invalidUserDto = new UserDto();
        invalidUserDto.setPassword("123");

        mockMvc.perform(post("/register").contentType(APPLICATION_JSON)
                                         .content(objectMapper.writeValueAsString(invalidUserDto)))
               .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostExistingUser_ThenThrowUserAlreadyExistsException() throws Exception {
        final UserDto existingUserDto = new UserDto(USER_DTO.getEmail(), "6789secret");

        doThrow(new UserAlreadyExistsException("User already exists for this email"))
                .when(userService).saveUser(existingUserDto);

        mockMvc.perform(post("/register").contentType(APPLICATION_JSON)
                                         .content(objectMapper.writeValueAsString(existingUserDto)))
               .andExpect(status().isConflict());
    }
}
