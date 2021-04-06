package com.intea.domain.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@ToString
@Entity
public class Cart extends CommonEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long i_cart;

    @ManyToOne
    @JoinColumn(name = "i_mem", referencedColumnName = "i_mem")
    private Members members;

    @ManyToOne
    @JoinColumn(name = "i_product", referencedColumnName = "i_product")
    private Product product;

    private Long count;
}
