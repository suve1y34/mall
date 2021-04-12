package com.intea.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intea.constant.ProductStatus;
import com.intea.domain.dto.ProductResDTO;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String large_ct;
    private String small_ct;

    private String p_nm;
    private Integer price;
    private String p_description;

    private Integer purchase_cnt;
    private Integer total_cnt;

    private ProductStatus product_status;

    private Integer stock;

    private String title_img;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductImg> productImgList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Cart> cartList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviewList;

    public ProductResDTO toResponseDTO(int price) {
        return ProductResDTO.builder()
                .id(id)
                .p_nm(p_nm)
                .large_ct(large_ct)
                .small_ct(small_ct)
                .price(price)
                .purchase_cnt(purchase_cnt)
                .total_cnt(total_cnt)
                .status(product_status)
                .title_img(title_img)
                .build();
    }

    public ProductResDTO.MainProductResDTO toMainProductResDTO() {
        return ProductResDTO.MainProductResDTO.builder()
                .id(id)
                .p_nm(p_nm)
                .title_img(title_img)
                .price(price)
                .timestamp(Timestamp.valueOf(this.getInsert_time()).getTime())
                .purchase_cnt(purchase_cnt)
                .build();
    }
}
