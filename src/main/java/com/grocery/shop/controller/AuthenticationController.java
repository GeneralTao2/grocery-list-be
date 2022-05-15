package com.grocery.shop.controller;

import com.grocery.shop.exception.UserNotFoundException;
import com.grocery.shop.model.LoginResponse;
import com.grocery.shop.model.LoginUser;
import com.grocery.shop.model.User;
import com.grocery.shop.repository.UserRepository;
import com.grocery.shop.security.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class AuthenticationController {
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;


    @PostMapping(value = "/login")
    public LoginResponse register(@RequestBody LoginUser loginUser) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword())
        );

        final User user = userRepository.findByEmail(loginUser.getEmail())
                .orElseThrow(UserNotFoundException::new);
        final String token = jwtTokenUtil.generateToken(user);
        return new LoginResponse(token, user.getEmail());
    }


}
