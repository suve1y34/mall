package com.intea.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopDTO extends ProductEntity {

    private Long i_cart;
    private Long i_mem;
    private int count;

}
