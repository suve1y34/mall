package com.intea.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ProductDisPriceResponseDto {

    private Long id;
    private Long productId;
    private String startDate;
    private String endDate;
    private int disPrice;
}
