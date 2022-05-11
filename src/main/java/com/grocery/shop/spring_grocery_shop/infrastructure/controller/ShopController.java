package com.grocery.shop.spring_grocery_shop.infrastructure.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Controller
public class ShopController {

//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/home").setViewName("home");
//        registry.addViewController("/").setViewName("home");
//        registry.addViewController("/hello").setViewName("hello");
//        registry.addViewController("/login").setViewName("login");
//    }

//    @GetMapping({"/", "/hello"})
//    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
//        model.addAttribute("name", name);
//        return "hello.jsp";
//    }

//    @GetMapping("/")
//    public String getInfoAllEmps(){
//        return "view_for_all_employees";
//    }
//
//    @GetMapping("/home")
//    public String getHome(){
//        return "home";
//    }
//
//    @GetMapping("/welcome")
//    public String getWelcome(){
//        return "welcome";
//    }
//
//    @GetMapping("/hello")
//    public String getHello(){
//        return "hello";
//    }

}
