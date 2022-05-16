package com.grocery.shop.model;

import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class JwtUserData {
    String email;
    List<SimpleGrantedAuthority> roles;
}
