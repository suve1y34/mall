package com.intea.domain.dto;

import com.intea.domain.entity.Cart;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class OrdersResponseDto {
    private Long id;
    private String orderNum;
    private String orderNm;
    private Integer amount;
    private String status;
    private String insertTime;

    private String message;
    private String postCode;
    private String address;
    private String deAddress;
    private Character refundState;
    private String phone;
    private List<Cart> cartList;
}
