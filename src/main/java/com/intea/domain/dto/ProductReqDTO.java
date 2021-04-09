package com.intea.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductReqDTO {
    @NotBlank(message = "상품명을 입력해 주세요.")
    private String p_nm;

    @NotNull(message = "상품 가격을 입력해 주세요.")
    private Integer price;

    @NotBlank(message = "이미지를 첨부해 주세요.")
    private String title_img;

    @NotBlank(message = "상위 카테고리를 작성해 주세요.")
    private String large_ct;
    @NotBlank(message = "하위 카테고리를 작성해 주세요.")
    private String small_ct;

    @NotNull(message = "상품 재고를 입력해 주세요.")
    private Integer total_cnt;
}
