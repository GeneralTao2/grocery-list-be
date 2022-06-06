package com.grocery.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
@Table(name = "customerDetails")
@SequenceGenerator(name = "customerDetails_generator", sequenceName = "customerDetails_seq", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetails {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerDetails_generator")
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Boolean keepMeInTouch;

    @Column
    @ManyToOne
    private Long addressId;
}
