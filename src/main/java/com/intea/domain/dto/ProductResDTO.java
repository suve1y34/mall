package com.intea.domain.dto;

import com.intea.constant.ProductStatus;
import lombok.*;

import java.io.Serializable;

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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MainProductResDTO implements Serializable {
        private Long id;
        private String p_nm;
        private String title_img;
        private Integer price;
        private Long timestamp;
        private Integer purchase_cnt;
    }
}
