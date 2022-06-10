package com.grocery.shop.service;

import com.grocery.shop.model.Cart;
import com.grocery.shop.model.Product;
import com.grocery.shop.model.ProductCategory;
import com.grocery.shop.model.Role;
import com.grocery.shop.model.Type;
import com.grocery.shop.model.User;
import com.grocery.shop.repository.CartRepository;
import com.grocery.shop.repository.ProductRepository;
import com.grocery.shop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

// FOR MANUAL TESTS PURPOSE
@Configuration
public class LoadDB {
    @Bean
    CommandLineRunner initDB(
            UserRepository userRepository,
            ProductRepository productRepository,
            CartRepository cartRepository,
            UserService userService
    ) {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            /*List<User> users = List.of(
                    new User("u@u.u", encoder.encode("123456"), Role.USER),
                    new User("q@q.q", encoder.encode("123456"), Role.USER),
                    new User("w@q.q", encoder.encode("123456"), Role.ADMIN)
            );

            users.forEach(user -> {
                ExampleMatcher modelMatcher = ExampleMatcher.matchingAny()
                        .withIgnorePaths("id")
                        .withMatcher("email", ignoreCase());
                Example<User> example = Example.of(user, modelMatcher);
                if(!userRepository.exists(example)) {
                    userService.saveUserWithCart(user);
                }
            });

            users = userRepository.findAll();


            List<Product> products = new ArrayList<>();
            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                products.add(new Product(0L, "https//:someImgNo" + i,
                        "name" + i, 500 + i, 1 + (5 - 1) * random.nextDouble(),
                        "d" + i, i, Type.WEIGHABLE, i, ProductCategory.FRUITS));
            }


            productRepository.saveAll(products);

            products = productRepository.findAll();
*/
            List<User> users = userRepository.findAll();
            List<Product> products = productRepository.findAll();

            Cart cart1 = cartRepository.findByUserId(users.get(0).getId());
            cart1.getCartItems().putAll(products.subList(10,12).stream()
                    .collect(Collectors.toMap(p -> p, p -> p.getSize() + 5)));
            cartRepository.save(cart1);


            Cart cart2 = cartRepository.findByUserId(users.get(1).getId());
            cart2.getCartItems().putAll(products.subList(20,22).stream()
                    .collect(Collectors.toMap(p -> p, p -> p.getSize() + 5)));
            cartRepository.save(cart2);



            Cart cart3 = cartRepository.findByUserId(users.get(0).getId());
            cart3.getCartItems().remove(
                    new ArrayList<>(cart3.getCartItems().keySet()).get(0)
            );
            cart3.getCartItems().remove(
                    new ArrayList<>(cart3.getCartItems().keySet()).get(1));
            cart3.getCartItems().replace(
                    new ArrayList<>(cart3.getCartItems().keySet()).get(2), 200);
            cartRepository.save(cart3);


        };
    }
}
