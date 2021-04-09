package com.intea.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class CategoryReqDTO {
    @NotBlank(message = "카테고리명을 작성해 주세요.")
    private String c_nm;

    @Getter
    @Setter
    @ToString
    public static class BigCategory {
        @NotBlank(message = "카테고리명을 작성해 주세요.")
        private String c_nm;
    }

    @Getter
    @Setter
    @ToString
    public static class SmallCategory {
        @NotBlank(message = "카테고리명을 작성해 주세요.")
        private String c_nm;
        @NotBlank(message = "상위 카테고리 코드를 작성해 주세요.")
        private String upper_c_code;
    }
}
