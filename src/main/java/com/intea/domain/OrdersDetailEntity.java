package com.intea.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersDetailEntity {
    private Long i_detail;
    private String i_order;
    private Long i_product;
    private Long stock;
}
