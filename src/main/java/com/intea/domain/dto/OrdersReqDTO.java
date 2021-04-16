package com.intea.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
public class OrdersReqDTO {
    private List<Long> cartIdList;

    @NotBlank(message = "주문번호는 필수 항목입니다.")
    private String order_num;

    @NotBlank(message = "주문명은 필수 항목입니다.")
    private String order_nm;
    private String message;
    @NotBlank(message = "주소는 필수 항목입니다.")
    private String postCode;
    @NotBlank(message = "주소는 필수 항목입니다.")
    private String address;
    @NotBlank(message = "주소는 필수 항목입니다.")
    private String de_address;
    private Integer amount;
    private Integer use_saving;
    private String impUid;
}
