package com.grocery.shop.security;

import com.grocery.shop.model.Role;
import com.grocery.shop.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
class JwtTokenUtilTest {
    @Mock
    JwtTokenUtil jwtTokenUtil;
    User user;

    @BeforeEach
    public void setUp(){
        jwtTokenUtil = new JwtTokenUtil();
        user = new User("user","password", Role.USER);

        String someName = "823dcb227a07d6d4b6b4f573a7bb75827597a17880b4e383b8f8b48a8dc0bab9";
        ReflectionTestUtils.setField(jwtTokenUtil,
                "SIGNING_KEY",
                someName);
    }

    @Test
    void shouldGetUsernameFromToken() {
        String actual = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.generateToken(user));
        String expected = "user";

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void shouldGetRoleFromToken() {
        List<SimpleGrantedAuthority> actual = jwtTokenUtil.getRoleFromToken(jwtTokenUtil.generateToken(user));

        assertThat(actual).hasSize(1);
        assertThat(actual).contains(new SimpleGrantedAuthority("USER"));
    }
}