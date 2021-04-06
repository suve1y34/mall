package com.intea.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersListDTO extends OrdersEntity {
    private Long i_detail;
    private String i_order;
    private Long i_product;
    private Long stock;

    private Long i_category;
    private String p_nm;
    private Long price;
    private String p_description;
    private String change_yn;
}
