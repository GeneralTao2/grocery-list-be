package com.grocery.shop.spring_grocery_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.grocery.shop.spring_grocery_shop"})
public class SpringGroceryShopApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure (SpringApplicationBuilder builder) {
        return builder.sources(SpringGroceryShopApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringGroceryShopApplication.class, args);
    }

}
