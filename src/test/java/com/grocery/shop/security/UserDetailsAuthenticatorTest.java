package com.grocery.shop.security;

import com.grocery.shop.dto.JwtUserData;
import com.grocery.shop.dto.UserDetailsImpl;
import com.grocery.shop.model.Role;
import com.grocery.shop.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsAuthenticatorTest {

    @Mock
    UserDetailsService userDetailsService;

    UserDetailsAuthenticator userDetailsAuthenticator;

    BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        reset(userDetailsService);
        userDetailsAuthenticator = new UserDetailsAuthenticator(userDetailsService);
        encoder = new BCryptPasswordEncoder();
    }

    @Test
    void authenticate() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("localhost");
        request.setRequestURI("/hey");

        final String email = "u@u.u";
        final Role role = Role.USER;
        final List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority(role.toString()));
        final JwtUserData jwtUserData = new JwtUserData(email, roles);
        final User user = new User(email, encoder.encode("123456"), role);
        final UserDetails userDetails = new UserDetailsImpl(user);

        when(userDetailsService.loadUserByUsername(jwtUserData.getEmail())).thenReturn(userDetails);

        userDetailsAuthenticator.authenticate(jwtUserData, request);
    }
}