package com.grocery.shop.spring_grocery_shop.infrastructure.controller;

import com.grocery.shop.spring_grocery_shop.domain.model.RegistrationRequest;
import com.grocery.shop.spring_grocery_shop.domain.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/registration")
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping()
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }
}
