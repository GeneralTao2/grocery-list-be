package com.grocery.shop.security;

import com.grocery.shop.dto.JwtUserData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtUserDataValidator {
    private final UserDetailsService userDetailsService;

    boolean validate(JwtUserData jwtUserData) {

        if (jwtUserData.getEmail() == null) {
            return false;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUserData.getEmail());

        if (userDetails == null) {
            return false;
        }

        if (!jwtUserData.getRoles().containsAll(userDetails.getAuthorities())) {
            return false;
        }

        return true;
    }
}
