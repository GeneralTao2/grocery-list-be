package com.grocery.shop.service;

import com.grocery.shop.dto.UserDto;
import com.grocery.shop.exception.UserAlreadyExistsException;
import com.grocery.shop.model.Cart;
import com.grocery.shop.model.Role;
import com.grocery.shop.model.User;
import com.grocery.shop.repository.CartRepository;
import com.grocery.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUserWithCart(User user) {
        User userWithId = userRepository.save(user);
        cartRepository.save(new Cart(userWithId));
    }

    @Override
    public void saveUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException("User already exists for this email");
        }
        final User user = toUserEntity(userDto);
        encodePassword(user, userDto);

        saveUserWithCart(user);
    }

    private static User toUserEntity(UserDto userDto) {
        final User user = new User();

        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(Role.USER);

        return user;
    }

    private void encodePassword(User user, UserDto userDto) {
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    }
}
