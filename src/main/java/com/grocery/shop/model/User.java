package com.grocery.shop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.validation.constraints.Email;

@Getter
@Setter
@Entity
@Table(name="users")
@SequenceGenerator(name = "default_generator", sequenceName = "user_seq", allocationSize = 1)
public class User extends BaseEntity{

    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role roles;

}
