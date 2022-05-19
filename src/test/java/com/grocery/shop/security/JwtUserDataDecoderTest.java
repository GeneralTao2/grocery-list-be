package com.grocery.shop.security;

import com.grocery.shop.dto.JwtUserData;
import com.grocery.shop.model.Role;
import com.grocery.shop.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static com.grocery.shop.security.JwtConstants.TOKEN_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;

class JwtUserDataDecoderTest {

    JwtUserDataDecoder jwtUserDataDecoder;

    JwtTokenUtil jwtTokenUtil;

    BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil();
        jwtUserDataDecoder = new JwtUserDataDecoder(jwtTokenUtil);
        encoder = new BCryptPasswordEncoder();
    }

    @Test
    void decode_PassGoodTokenWithUserData_ReturnFilledUserData() {
        final String email = "u@u.u";
        final Role role = Role.USER;
        final List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority(role.toString()));
        final JwtUserData jwtUserData = new JwtUserData(email, roles);
        final String password = "123456";
        final User user = new User(email, encoder.encode(password), role);
        final String jwtToken = TOKEN_PREFIX + jwtTokenUtil.generateToken(user);

        JwtUserData returnedJwtUserData = jwtUserDataDecoder.decode(jwtToken);

        assertThat(returnedJwtUserData).isEqualTo(jwtUserData);
    }

    @Test
    void decode_PassGoodTokenWithUserData_ReturnEmptyUserData() {
        final String email = "u@u.u";
        final Role role = Role.USER;
        final List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority(role.toString()));
        final JwtUserData jwtUserData = new JwtUserData(null, null);
        final String password = "123456";
        final User user = new User(email, encoder.encode(password), role);
        final String mistake = "dsfgdg";
        final String jwtToken = TOKEN_PREFIX + jwtTokenUtil.generateToken(user) + mistake;

        JwtUserData returnedJwtUserData = jwtUserDataDecoder.decode(jwtToken);

        assertThat(returnedJwtUserData).isEqualTo(jwtUserData);
    }
}