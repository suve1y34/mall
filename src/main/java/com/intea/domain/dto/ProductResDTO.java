package com.intea.domain.dto;

import com.intea.constant.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResDTO {
    private Long id;
    private String p_nm;
    private String title_img;
    private String large_ct;
    private String small_ct;
    private Integer price;
    private Integer purchase_cnt;
    private Integer total_cnt;
    private ProductStatus status;
}
