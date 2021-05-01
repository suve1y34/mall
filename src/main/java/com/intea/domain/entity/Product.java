package com.intea.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intea.common.CommonEntity;
import com.intea.domain.enums.ProductStatus;
import com.intea.domain.dto.ProductResponseDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity @Builder
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Product extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String largeCat;
    private String smallCat;

    private String productNm;
    private Integer price;

    private Integer purchaseCnt;
    private Integer limitCnt;
    private Integer totalCnt;

    private ProductStatus productStatus;

    private Integer stock;

    private String titleImg;

    @ColumnDefault("0")
    private Integer rateAvg;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductImg> productImgList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductDisPrice> productDisPrcList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Cart> cartList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviewList;

    public ProductResponseDto toResponseDTO(int disPrice) {
        return ProductResponseDto.builder()
                .id(id)
                .productNm(productNm)
                .largeCat(largeCat)
                .smallCat(smallCat)
                .price(price)
                .disPrice(disPrice)
                .purchaseCnt(purchaseCnt)
                .limitCnt(limitCnt)
                .totalCnt(totalCnt)
                .status(productStatus)
                .titleImg(titleImg)
                .rateAvg(rateAvg)
                .build();
    }

    public ProductResponseDto.MainProductResponseDto toMainProductResDTO(int disPrice) {
        return ProductResponseDto.MainProductResponseDto.builder()
                .id(id)
                .productNm(productNm)
                .titleImg(titleImg)
                .price(price)
                .disPrice(disPrice)
                .salePrice((int)((((float) 100 - (float) disPrice) / (float)100) * price))
                .timestamp(Timestamp.valueOf(this.getInsertTime()).getTime())
                .purchaseCnt(purchaseCnt)
                .build();
    }

    public ProductResponseDto.AdminProductResponseDto toAdminProductResDTO(int disPrice) {
        return ProductResponseDto.AdminProductResponseDto.builder()
                .id(id)
                .productNm(productNm)
                .titleImg(titleImg)
                .price(price)
                .disPrice(disPrice)
                .purchaseCnt(purchaseCnt)
                .totalCnt(totalCnt)
                .rateAvg(rateAvg)
                .build();
    }
}
