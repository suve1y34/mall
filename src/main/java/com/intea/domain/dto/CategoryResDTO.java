package com.intea.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CategoryResDTO {
    @Getter
    @Builder
    @ToString
    public static class BigCategory {
        private Long id;
        private String c_nm;
    }

    @Getter
    @Builder
    @ToString
    public static class SmallCategory {
        private Long id;
        private String c_nm;
        private String c_code;
        private String upper_c_code;
    }
}
