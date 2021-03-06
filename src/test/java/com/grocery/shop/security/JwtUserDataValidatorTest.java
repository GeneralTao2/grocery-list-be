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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUserDataValidatorTest {

    @Mock
    UserDetailsService userDetailsService;

    JwtUserDataValidator jwtUserDataValidator;

    BCryptPasswordEncoder encoder;


    @BeforeEach
    void setUp() {
        reset(userDetailsService);
        jwtUserDataValidator = new JwtUserDataValidator(userDetailsService);
        encoder = new BCryptPasswordEncoder();
    }

    @Test
    void validate_PassValidUserData_ReturnTrue() {
        final String email = "u@u.u";
        final Role role = Role.USER;
        final List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority(role.toString()));
        final JwtUserData jwtUserData = new JwtUserData(email, roles);
        final User user = new User(email, encoder.encode("123456"), role);
        final UserDetails userDetails = new UserDetailsImpl(user);

        when(userDetailsService.loadUserByUsername(jwtUserData.getEmail())).thenReturn(userDetails);

        boolean valid = jwtUserDataValidator.validate(jwtUserData);

        assertThat(valid).isTrue();
    }

    @Test
    void validate_PassInvalidUserData_ReturnTrue() {
        final String email = "u@u.u";
        final Role role = Role.USER;
        final List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority(role.toString()));
        final JwtUserData jwtUserData = new JwtUserData(email, roles);

        when(userDetailsService.loadUserByUsername(jwtUserData.getEmail())).thenReturn(null);

        boolean valid = jwtUserDataValidator.validate(jwtUserData);

        assertThat(valid).isFalse();
    }
}