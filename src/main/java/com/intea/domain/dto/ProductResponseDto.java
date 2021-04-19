package com.intea.domain.dto;

import com.intea.constant.ProductStatus;
import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {
    private Long id;
    private String productNm;
    private String titleImg;
    private String largeCat;
    private String smallCat;
    private Integer price;
    private Integer purchaseCnt;
    private Integer limitCnt;
    private Integer totalCnt;
    private ProductStatus status;
    private Integer rateAvg;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MainProductResDTO implements Serializable {
        private Long id;
        private String productNm;
        private String titleImg;
        private Integer price;
        private Long timestamp;
        private Integer purchaseCnt;
    }

    @Getter @Builder @ToString
    public static class AdminProductResDTO {
        private Long id;
        private String productNm;
        private String titleImg;
        private Integer price;
        private Integer purchaseCnt;
        private Integer totalCnt;
        private Integer rateAvg;
    }

    @Getter @Builder @ToString
    public static class AdminProductDetailResDTO {
        private Long id;
        private String productNm;
        private String titleImg;
        private Integer price;
        private String largeCat;
        private String smallCat;
        private Integer totalCnt;
    }
}
