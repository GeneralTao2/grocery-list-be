package com.grocery.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;


@Getter
@Setter
@Entity
@Table(name = "users")
@SequenceGenerator(name = "default_generator", sequenceName = "user_seq", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}