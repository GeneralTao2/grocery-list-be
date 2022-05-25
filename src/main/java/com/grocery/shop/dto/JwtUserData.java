package com.grocery.shop.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class JwtUserData {
    String email;
    List<SimpleGrantedAuthority> roles;
}
