package com.intea.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartEntity extends CommonEntity {
    private Long i_cart;
    private Long i_mem;
    private Long i_product;
    private int count;
}
