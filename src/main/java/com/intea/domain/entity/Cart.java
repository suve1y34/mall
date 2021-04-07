package com.intea.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class Cart extends CommonEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long i_cart;

    @ManyToOne
    @JoinColumn(name = "i_mem", referencedColumnName = "i_mem")
    private User user;

    @ManyToOne
    @JoinColumn(name = "i_product", referencedColumnName = "i_product")
    private Product product;

    private Long count;
}
