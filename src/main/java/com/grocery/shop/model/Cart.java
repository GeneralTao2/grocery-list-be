package com.grocery.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "default_generator", sequenceName = "cart_seq", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_generator")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cart_products", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "products_id")
    @Column(name = "quantity")
    private Map<Product, Integer> cartItems;

    public Cart(User user) {
        this.user = user;
    }
}
