package com.grocery.shop.service;

import com.grocery.shop.exception.UserNotFoundException;
import com.grocery.shop.model.LoginResponse;
import com.grocery.shop.model.LoginUser;
import com.grocery.shop.model.User;
import com.grocery.shop.repository.UserRepository;
import com.grocery.shop.security.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationService {
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    public LoginResponse login(LoginUser loginUser) {
        // todo
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword())
        );

        final User user = userRepository.findByEmail(loginUser.getEmail())
                .orElseThrow(UserNotFoundException::new);

        final String token = jwtTokenUtil.generateToken(user);

        return new LoginResponse(token, user.getEmail(), user.getRole().toString());
    }
}
