package com.intea.domain.dto;

import com.intea.domain.entity.Product;
import com.intea.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CartResponseDto {
    private Long id;
    private User user;
    private Product product;
    private Integer count;
}
