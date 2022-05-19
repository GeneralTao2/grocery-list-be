package com.grocery.shop.service;

import com.grocery.shop.model.Product;
import com.grocery.shop.model.Role;
import com.grocery.shop.model.User;
import com.grocery.shop.repository.ProductRepository;
import com.grocery.shop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

// FOR MANUAL TESTS PURPOSE
@Configuration
public class LoadDB {
    @Bean
    CommandLineRunner initDB(UserRepository userRepository, ProductRepository productRepository) {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userRepository.saveAll(List.of(
                            new User("u@u.u", encoder.encode("123456"), Role.USER),
                            new User("q@q.q", encoder.encode("123456"), Role.USER),
                            new User("w@q.q", encoder.encode("123456"), Role.ADMIN)
                    )
            );

            List<Product> products = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                products.add(new Product(0L, "i" + i,
                        "n" + i, 500.0 + i, 100.0 + i,
                        "d" + i));
            }

            productRepository.saveAll(products);
        };
    }
}
