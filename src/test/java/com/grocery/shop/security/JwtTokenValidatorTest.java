package com.grocery.shop.security;

import com.grocery.shop.model.Role;
import com.grocery.shop.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.grocery.shop.security.JwtConstants.TOKEN_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenValidatorTest {

    JwtTokenValidator jwtTokenValidator;

    JwtTokenUtil jwtTokenUtil;

    BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil();
        jwtTokenValidator = new JwtTokenValidator(jwtTokenUtil);
        encoder = new BCryptPasswordEncoder();
    }

    @Test
    void validate_GoodFreshToken_ReturnTrue() {
        final String email = "u@u.u";
        final Role role = Role.USER;
        final String password = "123456";
        final User user = new User(email, encoder.encode(password), role);
        final String jwtToken = TOKEN_PREFIX + jwtTokenUtil.generateToken(user);

        boolean valid = jwtTokenValidator.validate(jwtToken);

        assertThat(valid).isTrue();
    }

    @Test
    void validate_BadFreshToken_ReturnFalse() {
        final String email = "u@u.u";
        final Role role = Role.USER;
        final String password = "123456";
        final User user = new User(email, encoder.encode(password), role);
        final String jwtToken = jwtTokenUtil.generateToken(user);

        boolean valid = jwtTokenValidator.validate(jwtToken);

        assertThat(valid).isFalse();
    }
}