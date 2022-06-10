package com.grocery.shop.repository;

import com.grocery.shop.model.Cart;
import com.grocery.shop.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}
