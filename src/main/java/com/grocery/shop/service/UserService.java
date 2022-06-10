package com.grocery.shop.service;

import com.grocery.shop.dto.UserDto;
import com.grocery.shop.model.User;

public interface UserService {
    void saveUser(UserDto userDto);

    void saveUserWithCart(User user);
}
