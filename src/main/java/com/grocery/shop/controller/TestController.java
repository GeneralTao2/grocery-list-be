package com.grocery.shop.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// FOR MANUAL TESTS PURPOSE
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestController {

    @GetMapping("/hey")
    String hey() {
        return "Hey Hey!";
    }

    @GetMapping("/yo")
    String yo() {
        return "Yo!";
    }
}
