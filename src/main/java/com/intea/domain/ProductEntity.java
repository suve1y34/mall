package com.intea.domain;

import com.intea.constant.ProductStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEntity extends CommonEntity {
    //상품 번호, 큰 카테고리, 작은 카테고리, 상품명, 가격, 설명, 구매 수량, 재고 수량, 이미지
    private Long i_product;
    private Long large_ct;
    private Long small_ct;
    private String p_nm;
    private Long price;
    private String p_description;
    private Long purchase_cnt;
    private ProductStatus product_status;
    private Long stock;
    private String title_img;
}
