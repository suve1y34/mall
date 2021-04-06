package com.intea.domain.entity;

import com.intea.constant.ProductStatus;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@ToString
@Entity
public class Product extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long i_product;

    private String large_ct;
    private String small_ct;

    private String p_nm;
    private Long price;
    private String p_description;

    private Long purchase_cnt;

    private ProductStatus product_status;

    private Long stock;

    private String title_img;
}
