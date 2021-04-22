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
    private Integer disPrice;
    private Integer purchaseCnt;
    private Integer limitCnt;
    private Integer totalCnt;
    private ProductStatus status;
    private Integer rateAvg;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MainProductResponseDto implements Serializable {
        private Long id;
        private String productNm;
        private String titleImg;
        private Integer price;
        private Integer disPrice;
        private Integer salePrice;
        private Long timestamp;
        private Integer purchaseCnt;
        private Integer limitCnt;
        private Integer totalCnt;
        private ProductStatus productStatus;
        private Integer rateAvg;
    }

    @Getter @Builder @ToString
    public static class AdminProductResponseDto {
        private Long id;
        private String productNm;
        private String titleImg;
        private Integer price;
        private Integer disPrice;
        private Integer purchaseCnt;
        private Integer totalCnt;
        private Integer rateAvg;
    }

    @Getter @Builder @ToString
    public static class AdminProductDetailResponseDto {
        private Long id;
        private String productNm;
        private String titleImg;
        private Integer price;
        private Integer disPrice;
        private String disStartDate;
        private String disEndDate;
        private String largeCat;
        private String smallCat;
        private Integer totalCnt;
    }

    @Getter
    @Builder
    @ToString
    public static class SaleProductResponseDto {
        private Long productId;
        private String productNm;
        private String titleImg;
        private Integer price;
        private Integer disPrice;
        private Integer salePrice;
        private Integer rateAvg;
    }
}
