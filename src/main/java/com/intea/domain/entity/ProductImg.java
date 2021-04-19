package com.intea.domain.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
public class ProductImg extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;

    private Character basicYn;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;
}
