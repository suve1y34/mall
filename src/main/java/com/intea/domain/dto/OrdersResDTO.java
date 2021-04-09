package com.intea.domain.dto;

import com.intea.domain.entity.Cart;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class OrdersResDTO {
    private Long id;
    private String order_num;
    private String order_name;
    private Integer amount;
    private String status;
    private String insert_time;

    private String message;
    private String postCode;
    private String address;
    private String de_address;
    private String phone;
    private List<Cart> cartList;
}
