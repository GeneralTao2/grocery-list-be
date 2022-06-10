package com.grocery.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@SequenceGenerator(name = "product_generator", sequenceName = "product_seq", allocationSize = 1)
public class Product {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    private Long id;

    @Column
    private String image;

    @Column
    private String name;

    @Column
    private double price;

    @Column
    private double rate;

    @Column
    private String description;

    @Column
    private int countOfSoldProducts;

    @Enumerated(EnumType.STRING)
    @Column
    private Type type;

    @Column
    private int size;

    @Column
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
