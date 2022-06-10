package com.grocery.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "users")
@SequenceGenerator(name = "default_generator", sequenceName = "user_seq", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7437283172863781019L;

    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}