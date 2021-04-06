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
    private Long i_img;

    private String img_url;

    @ManyToOne
    @JoinColumn(name = "i_product", referencedColumnName = "i_product")
    private Product product;
}
