package com.intea.domain;

import com.intea.paging.Criteria;
import com.intea.paging.PaginationInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommonEntity extends Criteria {
    private PaginationInfo paginationInfo;
    private String delete_yn;
    private LocalDateTime insert_time;
    private LocalDateTime update_time;
    private LocalDateTime delete_time;
}
