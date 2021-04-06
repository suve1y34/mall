package com.intea.domain;

import com.intea.constant.ReviewStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO extends CommonEntity {
    private Long i_board;
    private Long i_product;
    private Long i_mem;
    private String title;
    private String content;
    private ReviewStatus grade;
    private Long view_cnt;
    private String secret_yn;

    /*
        계층형 게시판을 위한 추가 필드
        원글번호, 원글(답글포함)에 대한 순서, 답글계층
        origin_idx, group_ord, group_layer
     */

    private Long origin_idx;
    private Long group_ord;
    private Long group_layer;
}
