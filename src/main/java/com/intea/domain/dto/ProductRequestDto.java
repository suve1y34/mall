package com.intea.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductRequestDto {
    @NotBlank(message = "상품명을 입력해 주세요.")
    private String productNm;

    @NotNull(message = "상품 가격을 입력해 주세요.")
    private Integer price;

    @NotBlank(message = "이미지를 첨부해 주세요.")
    private String titleImg;

    @NotBlank(message = "상위 카테고리를 작성해 주세요.")
    private String largeCtCode;
    @NotBlank(message = "하위 카테고리를 작성해 주세요.")
    private String smallCtCode;

    @NotNull(message = "상품 재고를 입력해 주세요.")
    private Integer totalCnt;

    @Getter
    @Setter
    @ToString
    public static class UpdateResponseDto {
        @NotBlank(message = "상품명을 입력해 주세요.")
        private String productNm;

        @NotNull(message = "상품 가격을 입력해 주세요.")
        private Integer price;

        @NotBlank(message = "상위 카테고리를 입력해 주세요.")
        private String largeCtCode;

        @NotBlank(message = "하위 카테고리를 입력해 주세요.")
        private String smallCtCode;

        @NotNull(message = "상품 재고를 입력해 주세요.")
        private Integer totalCnt;
    }
}
