package com.intea.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ProductDisPriceRequestDto {
    private Long productId;
    @NotNull(message = "시작시간을 작성해 주세요.")
    private LocalDateTime startDate;
    @NotNull(message = "종료시간을 작성해 주세요.")
    private LocalDateTime endDate;
    @NotNull(message = "할인률을 작성해 주세요.")
    private int disPrice;
}
