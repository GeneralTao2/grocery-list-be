package com.grocery.shop.controller;

import com.grocery.shop.dto.LoginResponse;
import com.grocery.shop.dto.LoginUser;
import com.grocery.shop.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public LoginResponse login(@RequestBody @Valid LoginUser loginUser) throws AuthenticationException {
        return authenticationService.login(loginUser);
    }
}
