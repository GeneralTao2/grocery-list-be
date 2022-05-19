package com.grocery.shop.service;

import com.grocery.shop.dto.LoginResponse;
import com.grocery.shop.dto.LoginUser;
import com.grocery.shop.exception.UserNotFoundException;
import com.grocery.shop.model.User;
import com.grocery.shop.repository.UserRepository;
import com.grocery.shop.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginUser loginUser) {
        final User user = userRepository.findByEmail(loginUser.getEmail())
                .orElseThrow(UserNotFoundException::new);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword())
        );

        final String token = jwtTokenUtil.generateToken(user);

        return new LoginResponse(token, user.getEmail(), user.getRole().toString());
    }
}
