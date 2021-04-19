package com.intea.domain.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class PaymentRequestDto {
    @Getter
    @Builder
    public static class Success {
        private UUID userId;

        @NotBlank(message = "이메일을 작성해 주세요.")
        @Email(message = "이메일의 양식을 지켜서 작성해 주세요.")
        private String email;

        @NotBlank(message = "주문자 성함을 작성해 주세요.")
        private String buyerNm;

        @NotBlank(message = "주문번호를 작성해 주세요.")
        private String orderNum;

        @NotBlank(message = "주문명을 작성해 주세요.")
        private String orderNm;

        private String message;

        @NotBlank(message = "주소를 작성해 주세요.")
        private String poseCode;
        private String address;
        private String deAddress;

        private Integer amount;

        private Integer useSaving;

        private List<Long> cartIdList;
    }
}
