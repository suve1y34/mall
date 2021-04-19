package com.intea.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
public class OrdersRequestDto {
    private List<Long> cartIdList;

    @NotBlank(message = "주문번호는 필수 항목입니다.")
    private String orderNum;

    @NotBlank(message = "주문명은 필수 항목입니다.")
    private String orderNm;
    private String message;
    @NotBlank(message = "주소는 필수 항목입니다.")
    private String postCode;
    @NotBlank(message = "주소는 필수 항목입니다.")
    private String address;
    @NotBlank(message = "주소는 필수 항목입니다.")
    private String deAddress;
    private Integer amount;
    private Integer useSaving;
    private String impUid;
}
