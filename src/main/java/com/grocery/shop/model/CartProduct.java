package com.grocery.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="carts_products")
public class CartProduct {

    @EmbeddedId
    @JsonIgnore
    private CartProductPK pk;

    @Column(nullable = false)
    private Integer quantity;

//    public CartProduct(Cart cart, Product product, Integer quantity) {
//        pk = new CartProductPK();
//        pk.setCart(cart);
//        pk.setProduct(product);
//        this.quantity = quantity;
//    }
}
