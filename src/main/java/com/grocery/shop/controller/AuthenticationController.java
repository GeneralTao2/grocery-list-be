package com.grocery.shop.controller;

import com.grocery.shop.exception.UserNotFoundException;
import com.grocery.shop.model.LoginResponse;
import com.grocery.shop.model.LoginUser;
import com.grocery.shop.model.User;
import com.grocery.shop.repository.UserRepository;
import com.grocery.shop.security.JwtTokenUtil;
import com.grocery.shop.service.AuthenticationService;
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
    AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public LoginResponse login(@RequestBody LoginUser loginUser) throws AuthenticationException {
        return authenticationService.login(loginUser);
    }


}
