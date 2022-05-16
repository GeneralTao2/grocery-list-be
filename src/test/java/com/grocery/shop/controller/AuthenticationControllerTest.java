package com.grocery.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.shop.model.LoginResponse;
import com.grocery.shop.model.LoginUser;
import com.grocery.shop.security.JwtAuthenticationEntryPoint;
import com.grocery.shop.security.JwtUserDataDecoder;
import com.grocery.shop.security.JwtUserDataValidator;
import com.grocery.shop.security.UserDetailsAuthenticator;
import com.grocery.shop.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest
@ExtendWith(SpringExtension.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    JwtUserDataDecoder jwtUserDataDecoder;

    @MockBean
    JwtUserDataValidator jwtUserDataValidator;

    @MockBean
    UserDetailsAuthenticator userDetailsAuthenticator;

    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    ObjectMapper mapper;
    BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        reset(authenticationService);
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        encoder = new BCryptPasswordEncoder();
    }

    @Test
    void login_UserExistsAndPasswordCorrect_ReturnLoginResponse() throws Exception {
        LoginUser loginUser = new LoginUser("u@u.u", "123456");
        LoginResponse loginResponse = new LoginResponse(
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1Iiwic2NvcGVzIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dLCJleHAiOjE2NTI3MjAzOTl9.zghhKdnHVxcM_WBqS2x8akblOuHUkfIJYcwb0_o3aMw",
                "u@u.u",
                "USER"
        );
        String loginUserJson = mapper.writeValueAsString(loginUser);
        String path = "/login";
        int status = HttpStatus.OK.value();

        when(authenticationService.login(loginUser)).thenReturn(loginResponse);

        MockHttpServletResponse response = mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginUserJson))
                .andReturn()
                .getResponse();

        String body = response.getContentAsString();
        LoginResponse returnedLoginResponse = mapper.readValue(body, LoginResponse.class);

        assertThat(returnedLoginResponse).isEqualTo(loginResponse);
        assertThat(response.getStatus()).isEqualTo(status);
    }
}