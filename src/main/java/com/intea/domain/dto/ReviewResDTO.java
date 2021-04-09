package com.intea.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ReviewResDTO {

    private Long id;
    private UserResDTO.ReviewUserResDTO user_id;
    private String title;
    private int rate;
    private String insert_time;

    @Getter
    @Builder
    @ToString
    public static class ReviewDetailResDTO {
        private String content;
    }
}
