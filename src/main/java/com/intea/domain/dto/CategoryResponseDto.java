package com.intea.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CategoryResponseDto {
    @Getter
    @Builder
    @ToString
    public static class BigCategory {
        private Long id;
        private String catNm;
        private Character useYn;
    }

    @Getter
    @Builder
    @ToString
    public static class SmallCategory {
        private Long id;
        private String catNm;
        private String catCode;
        private String upprCatCode;
        private Character useYn;
    }
}
