package com.grocery.shop.service;

import com.grocery.shop.dto.LoginResponse;
import com.grocery.shop.dto.LoginUser;
import com.grocery.shop.exception.UserNotFoundException;
import com.grocery.shop.model.Role;
import com.grocery.shop.model.User;
import com.grocery.shop.repository.UserRepository;
import com.grocery.shop.security.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    AuthenticationManager authenticationManager;

    JwtTokenUtil jwtTokenUtil;

    AuthenticationService authenticationService;

    @BeforeEach
    public void setUp(){
        jwtTokenUtil = new JwtTokenUtil();
        authenticationService = new AuthenticationService(jwtTokenUtil, userRepository, authenticationManager);
    }

    @Test
    void login_ValidUser_ReturnLoginResponse() {
        String email = "u@uu.uu";
        LoginUser loginUser = new LoginUser(email, "123456");
        Role role = Role.USER;
        User user = new User(email,"is not important", role);
        String token = jwtTokenUtil.generateToken(user);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        LoginResponse actualLoginResponse = authenticationService.login(loginUser);

        assertThat(actualLoginResponse.getEmail()).isEqualTo(email);
        assertThat(actualLoginResponse.getToken()).isEqualTo(token);
        assertThat(actualLoginResponse.getRole()).isEqualTo(role.toString());
       }

    @Test
    void login_NotRegisteredUser_ThrowsException() {
        String email = "u@uu.uu";
        LoginUser loginUser = new LoginUser(email, "123456");

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> authenticationService.login(loginUser));
    }
}
