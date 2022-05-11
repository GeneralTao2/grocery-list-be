package com.grocery.shop.spring_grocery_shop.domain.service;

import com.grocery.shop.spring_grocery_shop.domain.model.RegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    public String register(RegistrationRequest request) {
        return "it works";
    }
}
