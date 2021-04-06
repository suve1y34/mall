package com.intea.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrdersEntity {
    /*
    1.주문 번호 2.회원 번호 3.수신자 3.총 가격 4.주문일시 5.우편번호 6.주소 7.상세주소 8.연락처 9.주문상태
     */
    private String i_order;
    private Long i_mem;
    private String rec;
    private Long amount;
    private LocalDateTime order_time;
    private String postCode;
    private String address;
    private String de_address;
    private String phone;
    private String delivery;
}
