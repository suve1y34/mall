package com.intea.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ReviewResponseDto {

    private Long id;
    private UserResponseDto.ReviewUserResDTO userId;
    private String title;
    private int rate;
    private String insertTime;

    @Getter
    @Builder
    @ToString
    public static class ReviewDetailResDTO {
        private String content;
    }
}
