package com.grocery.shop.security;

import com.grocery.shop.model.JwtUserData;
import com.grocery.shop.model.Role;
import com.grocery.shop.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static com.grocery.shop.security.JwtConstants.TOKEN_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    void decode_PassGoodTokenWithUserData_ReturnUserData() {
        String email = "u@u.u";
        Role role = Role.USER;
        List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority(role.toString()));
        JwtUserData jwtUserData = new JwtUserData(email, roles);
        User user = new User(0L, email, encoder.encode("u"), role);
        String jwtToken = TOKEN_PREFIX + jwtTokenUtil.generateToken(user);

        JwtUserData returnedJwtUserData = jwtUserDataDecoder.decode(jwtToken);

        assertThat(returnedJwtUserData).isEqualTo(jwtUserData);
    }
}