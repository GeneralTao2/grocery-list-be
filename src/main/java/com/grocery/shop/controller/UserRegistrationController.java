package com.grocery.shop.controller;

import com.grocery.shop.dto.UserDto;
import com.grocery.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
public class UserRegistrationController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerUser(@Valid @RequestBody final UserDto requestUserDto) {
        userService.saveUser(requestUserDto);

        return new ResponseEntity<>(CREATED);
    }
}
