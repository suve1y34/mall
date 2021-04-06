package com.intea.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentEntity extends CommonEntity {
    private Long i_cmt;
    private Long i_board;
    private Long i_mem;
    private String nm;
    private String content;
}
