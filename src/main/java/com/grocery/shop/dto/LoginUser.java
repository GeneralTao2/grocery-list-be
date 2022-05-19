package com.grocery.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 5, max = 10)
    private String password;
}
