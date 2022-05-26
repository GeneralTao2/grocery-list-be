package com.grocery.shop.controller;

import com.grocery.shop.security.JwtAuthenticationEntryPoint;
import com.grocery.shop.security.JwtTokenValidator;
import com.grocery.shop.security.JwtUserDataDecoder;
import com.grocery.shop.security.JwtUserDataValidator;
import com.grocery.shop.security.UserDetailsAuthenticator;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;

public class SecurityContextMocking {
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenValidator jwtTokenValidator;

    @MockBean
    private JwtUserDataDecoder jwtUserDataDecoder;

    @MockBean
    private JwtUserDataValidator jwtUserDataValidator;

    @MockBean
    private UserDetailsAuthenticator userDetailsAuthenticator;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
}
