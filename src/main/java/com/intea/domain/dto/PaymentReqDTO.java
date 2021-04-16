package com.intea.domain.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class PaymentReqDTO {
    @Getter
    @Builder
    public static class Success {
        private UUID user_id;

        @NotBlank(message = "이메일을 작성해 주세요.")
        @Email(message = "이메일의 양식을 지켜서 작성해 주세요.")
        private String email;

        @NotBlank(message = "주문자 성함을 작성해 주세요.")
        private String buyer_nm;

        @NotBlank(message = "주문번호를 작성해 주세요.")
        private String order_num;

        @NotBlank(message = "주문명을 작성해 주세요.")
        private String order_nm;

        private String message;

        @NotBlank(message = "주소를 작성해 주세요.")
        private String poseCode;
        private String address;
        private String de_address;

        private Integer amount;

        private Integer use_saving;

        private List<Long> cartIdList;
    }
}
