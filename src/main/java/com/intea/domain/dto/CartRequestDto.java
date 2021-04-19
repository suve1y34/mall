package com.intea.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import java.util.UUID;

@Getter
@Setter
@ToString
public class CartRequestDto {
    private UUID userId;
    private Long productId;
    @Min(value = 1, message = "최소수량은 1 입니다.")
    private Integer count;
}
