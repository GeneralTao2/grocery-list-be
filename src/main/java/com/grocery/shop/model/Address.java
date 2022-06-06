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

@Getter
@Setter
@Entity
@Table(name = "address")
@SequenceGenerator(name = "address_generator", sequenceName = "address_seq", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_generator")
    private Long id;

    @Column
    private String address;

    @Column
    private String addressDetails;

    @Column
    private String postalCode;

    @Column
    private String city;

    @Column
    private String country;
}
