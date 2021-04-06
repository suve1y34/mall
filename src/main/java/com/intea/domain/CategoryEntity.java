package com.intea.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryEntity {
    private Long i_category;
    private String c_code;
    private String c_nm;
    private Long ct_level;
}
